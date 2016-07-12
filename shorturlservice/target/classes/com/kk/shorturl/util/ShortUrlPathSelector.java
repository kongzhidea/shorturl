package com.kk.shorturl.util;

import com.kk.shorturl.model.Constant;

/**
 * 生成短链接的选择器，加密后的字符串，每次取Constant.SHORT_URL_PATH_LENGTH位作为短链接的长度
 * 
 * @author zhihui.kong@renren-inc.com
 * 
 */
public class ShortUrlPathSelector {
	/** 需要选取的字符串位置 */
	private int index = 0;
	/** 加密后的字符串 */
	private String shortUrlPathSourceStr;

	public ShortUrlPathSelector(String originalUrl) {
		this.shortUrlPathSourceStr = ByteUtil.byteArray2Str(MessageDigestUtil
				.computeDigest(originalUrl));
	}

	/**
	 * 判断是否还可以继续选取Constant.SHORT_URL_PATH_LENGTH长度的字符串
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return this.index < (this.shortUrlPathSourceStr.length() - Constant.SHORT_URL_PATH_LENGTH);
	}

	/**
	 * 选取长度为Constant.SHORT_URL_PATH_LENGTH的字符串
	 * 
	 * @return
	 */
	public String next() {
		String path = this.shortUrlPathSourceStr.substring(this.index,
				this.index + Constant.SHORT_URL_PATH_LENGTH);
		this.index++;
		return path;
	}
}