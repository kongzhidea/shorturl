package com.kk.shorturl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kk.shorturl.model.ShortUrl;
import com.kk.shorturl.service.ShortUrlService;
import com.kk.shorturl.service.ShortUrlServiceImpl;

public class ShortUrlTest {
	ShortUrlService shortUrlService;

	@Before
	public void setUp() throws Exception {
		shortUrlService = new ShortUrlServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void generateTest() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");
		String url = "http://jiepang.com/";
		// 97w8nTI4oCVad4hhmnpemCloj7gTaMYH6QkHo1V0h46

		ShortUrl shortUrl = shortUrlService.generateEncryptedUrl(url);
		System.out.println(shortUrl);
	}

	@Test
	public void getOriginalUrlTest() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");
		String encryp = "97w8nT";
		ShortUrl shortUrl = shortUrlService.getOrignalUrl(encryp);
		System.out.println(shortUrl);
	}

	@Test
	public void getOrignalUrlListTest() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");

		List<String> urls = new ArrayList<String>();
		urls.add("h3Qz6h");
		urls.add("4M81um");
		urls.add("rxYtqT");
		urls.add("uhMIch");
		urls.add("9ldMq3");
		List<ShortUrl> sts = shortUrlService.getOrignalUrlList(urls);
		for (ShortUrl st : sts) {
			System.out.println(st);
		}
	}

	@Test
	public void getOrignalUrlList2Test() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");

		List<String> urls = new ArrayList<String>();
		urls.add("h3Qz6h");
		urls.add("4M81um");
		urls.add("rxYtqT");
		urls.add("uhMIch");
		urls.add("9ldMq3");
		List<ShortUrl> sts = shortUrlService.getOrignalUrlList(urls
				.toArray(new String[1]));
		for (ShortUrl st : sts) {
			System.out.println(st);
		}
	}

	@Test
	public void generateEncryptedUrlStrTest() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");

		String body = "我是啥leizhe  <img src='http://fmnp.rrimg.com/fmn056/20131218/1550/p/m3w70h70q85lt_original_m7ZE_10340000a90e125f.jpg'/>"
				+ "哈哈"
				+ "<a href='http://jiepang.com/'>pub</a>"
				+ "<script src=\"http://test.kk.com/a69433/n/core/base-all2.js\"></script>";
		System.out.println(shortUrlService.generateEncryptedUrlStr(body, ""));

	}

	@Test
	public void generateEncryptedUrlStrsTest() {
		// System.out.println(Thread.currentThread().getStackTrace()[1]
		// .getMethodName() + "___________");
		//
		// String body =
		// "我是啥leizhe  <img src='http://fmnp.rrimg.com/fmn056/20131218/1550/p/m3w70h70q85lt_original_m7ZE_10340000a90e125f.jpg'/>"
		// + "哈哈"
		// + "<a href='http://jiepang.com/'>pub</a>"
		// +
		// "<script src=\"http://test.kk.com/a69433/n/core/base-all2.js\"></script>";
		// List<String> conts = new ArrayList<String>();
		// conts.add(body);
		// System.out.println(shortUrlService.generateEncryptedUrlStr(conts,
		// ""));

	}

	@Test
	public void generateEncryptedUrlBodyStrTest() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");

		String body = "我是啥leizhe  <img src='http://fmnp.rrimg.com/fmn056/20131218/1550/p/m3w70h70q85lt_original_m7ZE_10340000a90e125f.jpg'/>"
				+ "哈哈"
				+ "<a href='http://jiepang.com/'>pub</a>"
				+ "<script src=\"http://test.kk.com/a69433/n/core/base-all2.js\"></script>"
				+ "<li><a href=\"http://i.renren.com/\" class=\"optionvip\">VIP中心</a></li>"
				+ "<li><a href=\"http://pay.renren.com/\" class=\"optionpay\">充值中心</a></li></ul>";
		System.out.println(shortUrlService.generateEncryptedAUrlStr(body, ""));

	}

	@Test
	public void generateEncryptedUrlStatusStrTest() {
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName() + "___________");

		String body = "我来测试下百度:http://www.baidu.com";
		System.out.println(shortUrlService.generateEncryptedSUrlStr(body, ""));

	}
}
