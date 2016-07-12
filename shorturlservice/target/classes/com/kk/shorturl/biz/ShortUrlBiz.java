package com.kk.shorturl.biz;

import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kk.shorturl.dao.ShortUrlDao;
import com.kk.shorturl.model.ShortUrl;

/**
 * 后续可以改成spring集成方式来调用 TODO
 * 
 * 可以增加缓存操作
 * 
 * @author zhihui.kong@renren-inc.com
 * 
 */
public class ShortUrlBiz {
	private SqlSessionFactory sqlSessionFactory;
	private Reader reader;
	private SqlSession session;
	ShortUrlDao shorUrlDao;

	// 初始化
	public ShortUrlBiz() {
		try {
			reader = Resources
					.getResourceAsReader("com/kk/shorturl/mybatis/Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			session = sqlSessionFactory.openSession();
			shorUrlDao = session.getMapper(ShortUrlDao.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add(ShortUrl shortUrl) {
		shorUrlDao.add(shortUrl);
		session.commit();
	}

	public ShortUrl selectShortUrlByEncrypt(String encryptUrl) {
		return shorUrlDao.selectShortUrlByEncrypt(encryptUrl);
	}

	public int getCount(String encryptUrl) {
		return shorUrlDao.getCount(encryptUrl);
	}

	public void incrViewCount(String encryptUrl) {
		shorUrlDao.incrViewCount(encryptUrl);
		session.commit();
	}

	public List<ShortUrl> selectShortUrlByEncrypts(List<String> encryptUrls) {
		return shorUrlDao.selectShortUrlByEncrypts(encryptUrls);
	}

	public List<ShortUrl> selectShortUrlByEncrypts2(String[] encryptUrls) {
		return shorUrlDao.selectShortUrlByEncrypts2(encryptUrls);
	}

	/**
	 * 可以增加缓存 TODO
	 * 
	 * @param encryptUrl
	 * @return
	 */
	public ShortUrl notBeUsed(String encryptUrl) {
		return selectShortUrlByEncrypt(encryptUrl);
	}

	/**
	 * 可以增加缓存 TODO
	 * 
	 * @return
	 */
	public ShortUrl saveShortUrl(String originalUrl, String encryptedUrl) {
		ShortUrl url = new ShortUrl();
		url.setOriginalUrl(originalUrl);
		url.setEncryptedUrl(encryptedUrl);
		url.setCreateTime(new Date());
		url.setVisitCount(1);
		add(url);
		return url;
	}
}
