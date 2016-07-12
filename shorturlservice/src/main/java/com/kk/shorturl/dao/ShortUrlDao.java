package com.kk.shorturl.dao;

import java.util.List;

import com.kk.shorturl.model.ShortUrl;

public interface ShortUrlDao {

	ShortUrl selectShortUrlByEncrypt(String encryptUrl);

	public void add(ShortUrl shortUrl);

	public int getCount(String encryptUrl);

	public void incrViewCount(String encryptUrl);

	List<ShortUrl> selectShortUrlByEncrypts(List<String> encryptUrls);

	List<ShortUrl> selectShortUrlByEncrypts2(String[] encryptUrls);
}
