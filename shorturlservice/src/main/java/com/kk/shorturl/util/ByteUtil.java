/**
 * 
 */
package com.kk.shorturl.util;

import org.apache.commons.lang.ArrayUtils;

/**
 * 部门：技术部/UGC 组件/服务：shorturl 服务 <br>
 * 文件：BinaryUtil.java<br>
 * 功能说明：二进制处理的工具类<br>
 * wenfeng.jiang 2011-8-10
 */
public final class ByteUtil {

	private ByteUtil() {
	}

	/**
	 * 把bytes转换成二进制数
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteArray2Binary(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 8);
		StringBuffer tempSb = new StringBuffer(8);
		for (byte b : bytes) {
			tempSb.delete(0, tempSb.length());
			tempSb.append(Integer.toBinaryString(Math.abs(b)));
			while (tempSb.length() < 8) {
				tempSb.insert(0, '0');
			}
			sb.append(tempSb.toString());
		}
		return sb.toString();
	}

	static char[] BASESTR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();
	static int RADIX = 62;

	/**
	 * 生成加密后的字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteArray2Str(byte[] bytes) {
		if (ArrayUtils.isEmpty(bytes)) {
			return "";
		}
		String byteArray2Binary = byteArray2Binary(bytes);
		return binary2Str(byteArray2Binary, TranferNum.To62);
	}

	/**
	 * 二进制数转换成相应进制的字符串，现在就支持一种吧：62
	 * 
	 * @return
	 */
	public static String binary2Str(String binaryStr, TranferNum tranferNum) {
		int length = binaryStr.length();
		StringBuffer sb = new StringBuffer();
		int stepLength = 6;
		int step = length / stepLength;
		int mod = length % step;
		if (tranferNum == TranferNum.To62) {
			for (int i = 0; i < step; i++) {

				sb.append(BASESTR[Integer.valueOf(
						binaryStr.substring(i * stepLength, (i + 1)
								* stepLength), 2)
						% RADIX]);
			}
			if (mod > 0) {
				sb.append(BASESTR[Integer.valueOf(
						binaryStr.substring(step * stepLength, length), 2)]);
			}
		}
		return sb.toString();
	}

	public static enum TranferNum {
		To2(2, "二进制"), To8(8, "八进制"), To10(10, "10进制"), To16(16, "16进制"), To32(
				32, "32进制，大小写"), To62(62, "62进制,大小写加数字");
		private int num;
		private String desc;

		private TranferNum(int num, String desc) {
			this.num = num;
			this.desc = desc;
		}
	}
}
