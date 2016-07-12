package com.kk.shorturl.model;

import org.apache.commons.lang.StringUtils;

public class Constant {

	/**
	 * shortUrl path部分的长度
	 */
	public static final int SHORT_URL_PATH_LENGTH = 6;

	/**
	 * url字符串的最大字符长度，避免用户利用url进行破坏活动
	 */
	public static final int URL_MAX_LENGTH = 410;

	/**
	 * 短域名的前缀
	 * 
	 * @author tf
	 * 
	 */
	public static enum ShortUrlPrefix {

		RR_URL("http://rrurl.cn/");
		public String pre;

		private ShortUrlPrefix(String pre) {
			this.pre = pre;
		}
	}

	/**
	 * 这个白名单里面记载了不需要转换成短域名的url，鉴于这个需求不多，就直接写死不放在配置文件了
	 * 
	 * @author tf
	 */
	public static enum BlackList {
		RRURL("rrurl.cn"), TestURL("test.kk.com");
		;
		public String url;

		private BlackList(String url) {
			this.url = url;
		}
	}

	/**
	 * 判断一个url是不是以短连接开头
	 * 
	 * @param url
	 *            待判断url
	 * @return 如果有则返回true，如果没有则返回false
	 */
	public static boolean isShortUrlStr(String url) {
		return StringUtils.isNotBlank(url)
				&& url.trim().startsWith(ShortUrlPrefix.RR_URL.pre);
	}

	/**
	 * 判断一段文本中是否有我们转过的短连接，即是否包含 http://rrurl.cn/
	 * 
	 * @param text
	 *            待判断文本
	 * @return 如果有则返回true，如果没有则返回false
	 */
	public static boolean containsShortUrl(String text) {
		return StringUtils.isNotBlank(text)
				&& text.contains(ShortUrlPrefix.RR_URL.pre);
	}

	/**
	 * 判断给定的url是否给定的白名单里面
	 * 
	 * @param url
	 * @return
	 */
	public static boolean inBlackList(String url) {
		boolean result = false;
		BlackList[] whiteList = BlackList.values();
		for (BlackList wl : whiteList) {
			if (url.contains(wl.url)) {
				result = true;
			}
		}
		return result;
	}
}
