package com.kk.shorturl.model;

import java.io.Serializable;
import java.util.Date;

import com.kk.shorturl.model.Constant.ShortUrlPrefix;


public class ShortUrl implements Serializable {

	private static final long serialVersionUID = -7478796042927817356L;

	private long id;

	private String originalUrl;

	private String encryptedUrl;

	private Date createTime;

	private int visitCount;

	public ShortUrl() {
	}

	public ShortUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String format(FORMAT_TYPE type) {
		StringBuilder str = new StringBuilder();
		if (FORMAT_TYPE.ENCRYPTED_HREF_HTML.equals(type)) {
			str.append("<a href='");

			str.append(ShortUrlPrefix.RR_URL.pre);
			str.append(this.encryptedUrl);

			str.append("' target='_blank' title='");
			str.append(this.originalUrl);
			str.append("'>");
			str.append(ShortUrlPrefix.RR_URL.pre);
			str.append(this.encryptedUrl);
			str.append("</a>");
		} else if (FORMAT_TYPE.ENCRYPTED_PURE_TEXT.equals(type)) {
			str.append(ShortUrlPrefix.RR_URL.pre);
			str.append(this.encryptedUrl);
		}
		return str.toString();
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getEncryptedUrl() {
		return encryptedUrl;
	}

	public void setEncryptedUrl(String encryptedUrl) {
		this.encryptedUrl = encryptedUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	@Override
	public String toString() {
		return "ShortUrl [id=" + id + ", originalUrl=" + originalUrl
				+ ", encryptedUrl=" + encryptedUrl + ", createTime="
				+ createTime + ", visitCount=" + visitCount + "]";
	}

}
