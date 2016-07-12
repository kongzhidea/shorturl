package com.kk.shorturl.exception;

public class ShortUrlException extends RuntimeException {

	private static final long serialVersionUID = 8297297031089717900L;

	public ShortUrlException(String msg) {
		super(msg);
	}

	public ShortUrlException(Exception e) {
		super(e);
	}

}
