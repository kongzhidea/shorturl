package com.kk.shorturl.service;

import java.util.List;
import java.util.Map;

import com.kk.shorturl.exception.ShortUrlException;
import com.kk.shorturl.model.ShortUrl;

/**
 * 短链接 服务
 * 
 * @author zhihui.kong@renren-inc.com
 * 
 */
public interface ShortUrlService {

	/**
	 * 生成短链接
	 * 
	 * @param originalUrl
	 *            初始url
	 * @return 短链接url
	 * @throws ShortUrlException
	 */
	ShortUrl generateEncryptedUrl(String originalUrl) throws ShortUrlException;

	/**
	 * 根据一个转换过的字符串，找到这个shortUrl
	 * 
	 * @param encryptedUrl
	 * @return
	 * @throws ShortUrlException
	 */
	ShortUrl getOrignalUrl(String encryptedUrl) throws ShortUrlException;

	/**
	 * 根据一批转换过的字符串，找到这个shortUrl,不保证顺序
	 * 
	 * @param encryptedUrl
	 *            []
	 * @return
	 * @throws ShortUrlException
	 */
	List<ShortUrl> getOrignalUrlList(List<String> encryptedUrls)
			throws ShortUrlException;

	/**
	 * 根据一批转换过的字符串，找到这个shortUrl,不保证顺序
	 * 
	 * @param encryptedUrl
	 *            []
	 * @return
	 * @throws ShortUrlException
	 */
	List<ShortUrl> getOrignalUrlList(String[] encryptedUrls)
			throws ShortUrlException;

	/**
	 * 把originalContent中的url字符串生成相应的短域名字符串，短域名字符串
	 * 
	 * @Note 所有链接，包括图片，js，css等。
	 * @param originalContent
	 *            原始文本字符串集合
	 * @return 短域名字符串
	 */
	public String generateEncryptedUrlStr(String originalContent, String userIp);

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
	public Map<String, String> generateEncryptedUrlStr(
			List<String> originalContentList, String userIp);

	/**
	 * 把originalContent中的url字符串生成相应的短域名字符串，返回转换后的文本 仅仅替换<a>标签中的链接
	 * 
	 * @Note 适用于日志
	 * 
	 * @param originalContent
	 *            原始文本字符串集合
	 * @return
	 */
	String generateEncryptedAUrlStr(String originalContent, String userIp);

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
	String generateEncryptedSUrlStr(String originalContent, String userIp);
}
