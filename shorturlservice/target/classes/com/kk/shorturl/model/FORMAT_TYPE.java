package com.kk.shorturl.model;

public enum FORMAT_TYPE {
	/** 加上a标签 和域名 */
	ENCRYPTED_HREF_HTML(1), // <a href=''>http://rrurl.cn/yuerId</a>
	/** 加上域名 */
	ENCRYPTED_PURE_TEXT(2), // http://rrurl.cn/yuerId
	PURE_TEXT(3);
	private int style;

	private FORMAT_TYPE(int style) {
		this.style = style;
	}

	public int getValue() {
		return this.style;
	}

	public static FORMAT_TYPE valueOf(int style) {
		if (ENCRYPTED_HREF_HTML.getValue() == style) {
			return ENCRYPTED_HREF_HTML;
		} else if (ENCRYPTED_PURE_TEXT.getValue() == style) {
			return ENCRYPTED_PURE_TEXT;
		} else if (PURE_TEXT.getValue() == style) {
			return PURE_TEXT;
		}
		throw new IllegalArgumentException("invalid style:" + style);

	}
}
