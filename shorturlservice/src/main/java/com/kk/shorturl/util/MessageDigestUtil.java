package com.kk.shorturl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 加密算法
 * 
 * @author zhihui.kong@renren-inc.com
 * 
 */
public final class MessageDigestUtil {

	private MessageDigestUtil() {
	}

	public enum ALGORITHM {
		SHA {
			@Override
			public String getAlgorithmStr() {
				return "SHA";
			}
		},
		SHA256 {
			@Override
			public String getAlgorithmStr() {
				return "SHA-256";
			}
		},
		MD5 {
			@Override
			public String getAlgorithmStr() {
				return "MD5";
			}
		};
		public abstract String getAlgorithmStr();
	}

	/**
	 * 默认使用SHA256加密，返回32位长度字节数组
	 * 
	 * @param message
	 * @return
	 */
	public static byte[] computeDigest(String message) {
		return computeDigest(message, null);
	}

	public static byte[] computeDigest(String message, ALGORITHM algorithm) {
		if (StringUtils.isBlank(message)) {
			return new byte[0];
		}

		final ALGORITHM algoti = setDefaultAlgorithmIfNull(algorithm);

		final MessageDigest digest = getMessageDigestInstance(algoti);
		digest.reset();
		digest.update(message.getBytes());
		return digest.digest(message.getBytes());
	}

	private static ALGORITHM setDefaultAlgorithmIfNull(ALGORITHM algorithm) {
		ALGORITHM algoti = algorithm;
		if (algoti == null) {
			algoti = ALGORITHM.SHA256;
		}
		return algoti;
	}

	private static MessageDigest getMessageDigestInstance(ALGORITHM algorithm) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm.getAlgorithmStr());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digest;
	}

	public static void main(String[] args) {
		String originalUrl = "http://www.baidu.com";
		String shortUrlPathSourceStr = ByteUtil.byteArray2Str(MessageDigestUtil
				.computeDigest(originalUrl));
		System.out.println(shortUrlPathSourceStr);// 97w8nTI4oCVad4hhmnpemCloj7gTaMYH6QkHo1V0h46

	}
}
