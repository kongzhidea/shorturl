package com.kk.shorturl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.kk.shorturl.model.Constant;
import com.kk.shorturl.model.FORMAT_TYPE;
import com.kk.shorturl.model.ShortUrl;

public class ContentUtil {
	// 获取a标签
	private static String regex = "<a.*?>";
	// 获取链接内容
	private static Pattern pattern = Pattern.compile(regex);

	/** url正则 不包括中文 不包括常见的中文标点 ，。、【】（）“ ？ ； ！ ‘ */
	public static final String URL_REGEX = "(https?|ftp|gopher|telnet|prospero|wais|nntp){1}://\\w*((?![\"| |\t|\r|\n|[\u4E00-\u9FA5]"
			+ "|！|'|￥|……|（|）|——|【|】|、|；|：|’|‘|《|》|，|。|？|”|“|\\(|\\)|<|>|@]).)+";
	/** 提取正文中所有链接 */
	public static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX,
			Pattern.CASE_INSENSITIVE);

	/**
	 * 调整为获取a标签的前段
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getUrlList(String context) {
		if (StringUtils.isBlank(context)) {
			return null;
		}
		List<String> urlList = new ArrayList<String>();
		Matcher matcher = pattern.matcher(context);
		while (matcher.find()) {
			urlList.add(matcher.group());
		}
		return urlList;
	}

	/**
	 * 得到content中的所有链接
	 * 
	 * @param originalContent
	 * @return
	 */
	public static List<String> parseShortUrlString(String originalContent) {
		if (StringUtils.isBlank(originalContent)) {
			return new ArrayList<String>();
		}
		List<String> shortUrlStrList = new ArrayList<String>();
		Matcher matcher = URL_PATTERN.matcher(originalContent);
		while (matcher.find()) {
			String group = matcher.group();
			if (StringUtils.isNotBlank(group)) {
				if (group.length() <= Constant.URL_MAX_LENGTH) {
					shortUrlStrList.add(group);
				}
			}
		}
		return shortUrlStrList;
	}

	/**
	 * 判断url是否是一个链接
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isLink(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		Matcher matcher = URL_PATTERN.matcher(url);
		return matcher.matches();
	}

	/**
	 * 替换文本中的链接为短链接
	 * 
	 * @param originalContent
	 * @param shortUrlBeans
	 * @return
	 */
	public static String format(String originalContent,
			List<ShortUrl> shortUrlBeans, FORMAT_TYPE type) {
		if (StringUtils.isBlank(originalContent)) {
			return "";
		}
		// 去重
		Collection<ShortUrl> notRepeatShortUrls = filterSameShortUrl(shortUrlBeans);

		String formatedStr = originalContent;
		if (CollectionUtils.isNotEmpty(notRepeatShortUrls)) {
			if (notRepeatShortUrls.size() == 1) {
				for (ShortUrl shortUrl : notRepeatShortUrls) {
					formatedStr = StringUtils.replace(formatedStr,
							shortUrl.getOriginalUrl(), shortUrl.format(type));
				}
			} else {
				List<ShortUrl> shortUrlList = new ArrayList<ShortUrl>();
				for (ShortUrl shortUrl : notRepeatShortUrls) {
					shortUrlList.add(shortUrl);
				}
				// 越长的越在前面
				Collections.sort(shortUrlList, new Comparator<ShortUrl>() {
					public int compare(ShortUrl o1, ShortUrl o2) {
						if (o1 == null || o1.getOriginalUrl() == null) {
							return 1;
						}
						if (o2 == null || o2.getOriginalUrl() == null) {
							return -1;
						}
						return o2.getOriginalUrl().length()
								- o1.getOriginalUrl().length();
					}
				});

				for (ShortUrl shortUrl : shortUrlList) {
					formatedStr = StringUtils.replace(formatedStr,
							shortUrl.getOriginalUrl(), shortUrl.format(type));
				}
			}

		}
		return formatedStr;
	}

	public static List<ShortUrl> filterSameShortUrl(List<ShortUrl> shortUrlBeans) {
		if (CollectionUtils.isEmpty(shortUrlBeans) || shortUrlBeans.size() <= 1) {
			return shortUrlBeans;
		}
		Map<String, ShortUrl> tempMap = new ConcurrentHashMap<String, ShortUrl>(
				shortUrlBeans.size());
		for (ShortUrl url : shortUrlBeans) {
			tempMap.put(url.getOriginalUrl(), url);
		}
		List<ShortUrl> ret = new ArrayList<ShortUrl>();
		for (String key : tempMap.keySet()) {
			ret.add(tempMap.get(key));
		}
		return ret;
	}
}
