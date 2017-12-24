package com.sf.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class HttpClient {
	
	public static String get(String pageUrl, String charSet, String proxyIp, int proxyPort) {
		
		try {
			URL url = new URL(pageUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort)));
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate, br");
			conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
			conn.setRequestProperty("Connection","keep-alive");
			conn.setRequestProperty("Host","book.douban.com");
			conn.setRequestProperty("Referer","https://book.douban.com/");
			conn.setRequestProperty("Upgrade-Insecure-Requests","1");
			conn.setReadTimeout(60000);
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			conn.setRequestProperty("Cookie","bid=f7w7wt4o2s8; __utmz=81379588.1513926527.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); gr_user_id=74f97282-a270-40d8-8f63-cf8db2cbd4db; _vwo_uuid_v2=41776FDAB0C813F7739DBC56AC4ABFDC|a7a3a5612c82839efddd2f583d7d878c; ap=1; __yadk_uid=SkgzQceC556bJrjAi4d6h06FrNVOvMNU; ll='108296'; ps=y; __utmz=30149280.1513991020.5.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; dbcl2='171443076:LvJpk9LGQKk'; push_noty_num=0; push_doumail_num=0; __utmv=30149280.17144; ck=GXCj; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=ac6e7238-745b-4d96-bc12-1dacc18da2ad; gr_cs1_ac6e7238-745b-4d96-bc12-1dacc18da2ad=user_id%3A1; _pk_id.100001.3ac3=72d6cfed7662b0a4.1513926527.4.1514009466.1513992407.; _pk_ses.100001.3ac3=*; __utma=30149280.1249935076.1513926527.1513991020.1514009466.6; __utmc=30149280; __utmt_douban=1; __utmb=30149280.1.10.1514009466; __utma=81379588.1810684881.1513926527.1513991084.1514009466.4; __utmc=81379588; __utmt=1; __utmb=81379588.1.10.1514009466");
			conn.connect();
			
			if (conn.getResponseCode() == 404) {
				return null;
			}
			BufferedReader reader = null;
			if (conn.getHeaderField("Content-Encoding").equals("gzip")) {
				reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charSet));
			} else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
			}
			 
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			return sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
public static String get2(String pageUrl, String charSet) {
		
		try {
			URL url = new URL(pageUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate, br");
			conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
			conn.setRequestProperty("Connection","keep-alive");
			conn.setRequestProperty("Host","book.douban.com");
			conn.setRequestProperty("Referer","https://book.douban.com/");
			conn.setRequestProperty("Upgrade-Insecure-Requests","1");
			conn.setReadTimeout(60000);
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			conn.setRequestProperty("Cookie","bid=f7w7wt4o2s8; __utmz=81379588.1513926527.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); gr_user_id=74f97282-a270-40d8-8f63-cf8db2cbd4db; _vwo_uuid_v2=41776FDAB0C813F7739DBC56AC4ABFDC|a7a3a5612c82839efddd2f583d7d878c; ap=1; __yadk_uid=SkgzQceC556bJrjAi4d6h06FrNVOvMNU; ll='108296'; ps=y; __utmz=30149280.1513991020.5.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; dbcl2='171443076:LvJpk9LGQKk'; push_noty_num=0; push_doumail_num=0; __utmv=30149280.17144; ck=GXCj; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=ac6e7238-745b-4d96-bc12-1dacc18da2ad; gr_cs1_ac6e7238-745b-4d96-bc12-1dacc18da2ad=user_id%3A1; _pk_id.100001.3ac3=72d6cfed7662b0a4.1513926527.4.1514009466.1513992407.; _pk_ses.100001.3ac3=*; __utma=30149280.1249935076.1513926527.1513991020.1514009466.6; __utmc=30149280; __utmt_douban=1; __utmb=30149280.1.10.1514009466; __utma=81379588.1810684881.1513926527.1513991084.1514009466.4; __utmc=81379588; __utmt=1; __utmb=81379588.1.10.1514009466");
			conn.connect();
			
			if (conn.getResponseCode() == 404) {
				return null;
			}
			BufferedReader reader = null;
			if (conn.getHeaderField("Content-Encoding").equals("gzip")) {
				reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charSet));
			} else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
			}
			 
			String line = null;
			StringBuilder sb = new StringBuilder();
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			return sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	

}
