package com.kk.shorturl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kk.shorturl.biz.ShortUrlBiz;
import com.kk.shorturl.exception.ShortUrlException;
import com.kk.shorturl.model.Constant;
import com.kk.shorturl.model.FORMAT_TYPE;
import com.kk.shorturl.model.ShortUrl;
import com.kk.shorturl.util.ContentUtil;
import com.kk.shorturl.util.ShortUrlPathSelector;

/**
 * 短连接服务
 * 
 * @author zhihui.kong@renren-inc.com
 * 
 */
public class ShortUrlServiceImpl implements ShortUrlService {

	private static final Log logger = LogFactory
			.getLog(ShortUrlServiceImpl.class);

	ShortUrlBiz shortUrlBiz = new ShortUrlBiz();

	/**
	 * 生成短链接
	 * 
	 * @param originalUrl
	 * @return 如果hash失败，则返回的ShortUrl 中的id为0
	 * @throws ShortUrlException
	 */
	@Override
	public ShortUrl generateEncryptedUrl(String originalUrl)
			throws ShortUrlException {

		checkLegalUrl(originalUrl);

		ShortUrl tempBean = new ShortUrl(originalUrl);

		ShortUrlPathSelector selecotr = new ShortUrlPathSelector(originalUrl);
		ShortUrl dbBean = null;
		while (selecotr.hasNext()) {
			// 加密后的短链接
			String encryptUrl = selecotr.next();
			// 先查数据库中有没有已经保存过该链接
			if ((dbBean = shortUrlBiz.notBeUsed(encryptUrl)) == null) {
				// 如果没有则入库
				tempBean = shortUrlBiz.saveShortUrl(originalUrl, encryptUrl);
				break;
			} else if (dbBean.getOriginalUrl().equals(originalUrl)) {
				// 如果有则增加访问次数
				shortUrlBiz.incrViewCount(encryptUrl);
				tempBean = dbBean;
				break;
			}
		}
		// 如果短链接都被占用了，即hash冲突
		if (!selecotr.hasNext()) {
			tempBean.setEncryptedUrl(originalUrl);
		}
		if (tempBean.getId() == 0) {
			return null;
		}
		return tempBean;
	}

	private void checkLegalUrl(String originalUrl) {
		if (StringUtils.isBlank(originalUrl)) {
			throw new ShortUrlException("url为空");
		}
		if (originalUrl.length() > Constant.URL_MAX_LENGTH) {
			throw new ShortUrlException("url太长");
		}
		if (!ContentUtil.isLink(originalUrl)) {
			throw new ShortUrlException("url格式不正确");
		}
		if (Constant.isShortUrlStr(originalUrl)) {
			throw new ShortUrlException("该url已经是短链接");
		}
		if (Constant.inBlackList(originalUrl)) {
			throw new ShortUrlException("已被加入黑名单，不能转换短链接");
		}
	}

	/**
	 * 根据一个转换过的字符串，找到这个shortUrl
	 * 
	 * @param encryptedUrl
	 * @return
	 * @throws ShortUrlException
	 */
	@Override
	public ShortUrl getOrignalUrl(String encryptedUrl) throws ShortUrlException {
		ShortUrl shortUrl = shortUrlBiz.selectShortUrlByEncrypt(encryptedUrl);
		shortUrlBiz.incrViewCount(encryptedUrl);
		return shortUrl;
	}

	/**
	 * 根据一批转换过的字符串，找到这个shortUrl,不保证顺序
	 * 
	 * @param encryptedUrl
	 * @return
	 * @throws ShortUrlException
	 */
	@Override
	public List<ShortUrl> getOrignalUrlList(List<String> encryptUrls)
			throws ShortUrlException {
		return shortUrlBiz.selectShortUrlByEncrypts(encryptUrls);
	}

	/**
	 * 根据一批转换过的字符串，找到这个shortUrl,不保证顺序
	 * 
	 * @param encryptedUrl
	 * @return
	 * @throws ShortUrlException
	 */
	@Override
	public List<ShortUrl> getOrignalUrlList(String[] encryptUrls)
			throws ShortUrlException {
		return shortUrlBiz.selectShortUrlByEncrypts2(encryptUrls);
	}

	/**
	 * 把originalContent中的所有url字符串生成相应的短域名字符串，短域名字符串
	 * 
	 * @Note 所有链接，包括图片，js，css等。
	 * @param originalContent
	 *            原始文本字符串集合
	 * @return 短域名字符串
	 */
	@Override
	public String generateEncryptedUrlStr(String originalContent, String userIp) {
		// 获取文章中的所有链接
		List<String> urlStrList = ContentUtil
				.parseShortUrlString(originalContent);
		// 将这些链接转成短链接
		List<ShortUrl> shortUrls = new ArrayList<ShortUrl>();
		for (String url : urlStrList) {
			try {
				ShortUrl shortUrl = generateEncryptedUrl(url);
				shortUrls.add(shortUrl);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		// 替换文本中的链接
		return ContentUtil.format(originalContent, shortUrls,
				FORMAT_TYPE.ENCRYPTED_PURE_TEXT);
	}

	/**
	 * 把originalContentList中的url字符串生成相应的短域名字符串，返回Map<原始文本，短域名字符串>
	 * 
	 * @Note 所有链接，包括图片，js，css等。
	 * @param originalContentList
	 *            原始文本字符串集合
	 * @param type
	 * @see {@link FORMAT_TYPE}
	 * @return Map<String,String> Map<原始文本，短域名字符串>
	 */
	@Override
	public Map<String, String> generateEncryptedUrlStr(
			List<String> originalContentList, String userIp) {
		if (CollectionUtils.isEmpty(originalContentList)) {
			return new HashMap<String, String>(0);
		}
		Map<String, String> result = new HashMap<String, String>(
				originalContentList.size());
		for (String originalContent : originalContentList) {
			String encryptedUrlStr = generateEncryptedUrlStr(originalContent,
					userIp);
			if (StringUtils.isNotBlank(encryptedUrlStr)) {
				result.put(originalContent, encryptedUrlStr);
			}
		}
		return result;
	}

	/**
	 * 把body中的url字符串生成相应的短域名字符串，返回转换后的文本 仅仅替换<a>标签中的链接
	 * 
	 * @Note 适用于日志
	 * @param originalContent
	 *            原始文本字符串集合
	 * @return
	 */
	@Override
	public String generateEncryptedAUrlStr(String body, String userIp) {
		if (StringUtils.isBlank(body)) {
			return body;
		}
		// 获取日志中的a标签 <a href="http://www.baidu.com">
		List<String> urlList = ContentUtil.getUrlList(body);
		if (urlList == null || urlList.size() <= 0) {
			return body;
		}
		Map<String, String> map = generateEncryptedUrlStr(urlList, userIp);
		for (String string : map.keySet()) {
			String url = map.get(string);
			if (StringUtils.isNotBlank(url)) {
				body = body.replace(string, url);
			}
		}
		return body;
	}

	/**
	 * 把originalContent中的url字符串生成相应的短域名字符串，返回转换后的文本
	 * 
	 * 替换所有的链接，并且前后加上<a>标签
	 * 
	 * @Note 适用于状态
	 * 
	 * @param originalContent
	 *            原始文本字符串集合
	 * @return
	 */
	@Override
	public String generateEncryptedSUrlStr(String originalContent, String userIp) {
		// 获取文章中的所有链接
		List<String> urlStrList = ContentUtil
				.parseShortUrlString(originalContent);
		// 将这些链接转成短链接
		List<ShortUrl> shortUrls = new ArrayList<ShortUrl>();
		for (String url : urlStrList) {
			try {
				ShortUrl shortUrl = generateEncryptedUrl(url);
				shortUrls.add(shortUrl);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		// 替换文本中的链接
		return ContentUtil.format(originalContent, shortUrls,
				FORMAT_TYPE.ENCRYPTED_HREF_HTML);
	}

}
