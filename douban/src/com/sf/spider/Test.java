package com.sf.spider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sf.Util.JDBCUtil;




public class Test {
	
	private static ExecutorService pool = Executors.newFixedThreadPool(1);
	
	/*用线程池过快容易被封，一，可以构建ip代理池，但是需要花钱买，这里就只是构建了ip代理池能运行起来
	但是，没有加入此次爬书籍数据的代码中；二,User Agent ,禁Cookie,设置较大的时间间隔防止被封*/
	public static void main(String[] args)  {
			
		//HttpClient中封装了两个get请求方式，一种是需要代理Ip,一种是自己ip
		// 分别插入 算法 互联网 编程
			List<String> urls = downLoadUrls("编程", "61.135.217.7", 80);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//此方法内部用到了线程池
			getInfo(urls, "122.114.31.177", 808);
	
		
		
	}
	
	//代理线程池 因为没有买ip,免费ip好多失效的，所以没法开启线程池去快速获取书的信息，所以这里只写了思路
	public static void ip() {

			List<Map<String, Integer>> listIp = ipPoolTests();
			for (int i = 0; i < 100; i++) {
				Map<String, Integer> map = random(listIp);
				String ip = new ArrayList<>(map.keySet()).get(0);
				int port = map.get(ip);
				System.out.println(ip + " " + port);
			
				System.out.println(ip + " " + port);
			
				String html = HttpClient.get("http://www.xicidaili.com", "utf-8", ip, port);
				System.out.println(html);
			}
			
			
	}
	
	//根据爬出来的ip数量随机产出其中一个ip的map
	private static Map<String, Integer> random(List<Map<String, Integer>> list) {
		return list.get(new Random().nextInt(list.size()));
	}
	
	
	//构建代理IP池，所以先抓取代理网站的ip
	private static List<Map<String,Integer>> ipPoolTests() {
		
		//添加ip及端口
		List<Map<String,Integer>> list = new ArrayList<>();
		
		String html = HttpClient.get2("http://www.xicidaili.com/nn", "utf-8");
		Element table = Jsoup.parse(html).getElementById("ip_list");
		Elements trs = table.select("tr");
		int i = 0;
		for (Element tr : trs) {
			if (i > 0) {
				Elements tds = tr.select("td");
				Map<String,Integer> map = new HashMap<>();			
				//System.out.println(tds.get(1).text() + " " + tds.get(2).text());
				map.put(tds.get(1).text(), Integer.parseInt(tds.get(2).text()));
				list.add(map);
			}
			i++;
		}
		
		return list;
	}
	
	
	//下载书的url
	private static List<String> downLoadUrls(String keyword, String ip, int myPort) {
		List<String> booksUrl = new ArrayList<>();
		int index = 0;
		
		while (true) {
			//String html  = HttpClient.get("https://book.douban.com/tag/"+ keyword + "?start="+ index +"&type=T", "utf-8", ip, myPort);
			String html  = HttpClient.get2("https://book.douban.com/tag/"+ keyword + "?start="+ index +"&type=T", "utf-8");
			//Document document = Jsoup.connect("https://book.douban.com/tag/算法?start=0&type=T")
			//		.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36").cookies(cookies)
			//		.timeout(60000).get();
			Document document = Jsoup.parse(html);
			
			Elements elements = document.select("ul").select("h2").select("a");
			
			//本页的书的url数量
			System.out.println("本页书url的数量:"+ elements.size() );
			
			for (Element element2 : elements) {
				booksUrl.add(element2.attr("href"));
				System.out.println(element2.attr("href"));
			}
			
			index += elements.size();
			System.out.println("已爬书籍数量" + index);
			if (elements.size() == 0) {
				System.out.println("已经没有书了");
				break;//跳出当前循环
			}
			
			try {
				//过快ip会被封，睡眠防止封ip
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			return booksUrl;
	}
	
	//根据下载的书的url然后进入到其url获取具体数据
	private static void getInfo(List<String> bookUrls, String ip, int port) {
		 
		CountDownLatch latch = new CountDownLatch(bookUrls.size());
		int count = 0;
		for (String url : bookUrls) {
			//开启线程池
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					try {
						//String html = HttpClient.get(url, "utf-8", ip, port);
						String html = HttpClient.get2(url, "utf-8");
						Document document = Jsoup.parse(html);
						
						Elements titleElement = document.getElementsByClass("subject clearfix").select("a");
						Elements scoreElement = document.select("strong");
						Elements ratingSum = document.getElementsByClass("rating_sum").select("a").select("span");
						Elements authorElement = document.getElementById("info").select("span").first().select("a");
						Element pressElement = document.getElementById("info");
						
						//书名
						String title = titleElement.attr("title");
						//评分
						String score = scoreElement.html();
						//评价人数
						String rating_sum = ratingSum.html();
						//作者
						String author = authorElement.html();
						//出版社
						String press = pressElement.text();
						//逻辑判断找出出版社
						if (press.indexOf("出版社:") > -1) {
							press = pressElement.text().split("出版社:")[1].split(" ")[1];
						} else {
							press = "";
						}
						//逻辑判断出版日期
						String date = pressElement.text();
						if (date.indexOf("出版年:") > -1) {
							date = pressElement.text().split("出版年:")[1].split(" ")[1];
						} else {
							date = "";
						}
						//价格
						String price = pressElement.text();
						if (price.indexOf("定价:") > -1) {
							price = pressElement.text().split("定价:")[1].split(" ")[1];
							if (price.equals("CNY")) {
								price = pressElement.text().split("定价:")[1].split(" ")[2];
							}
						} else {
							price = "";
						}

						System.out.println(title);
						
						//存入到数据库
						
						String sql = "insert into books(title,score,rating_sum,author,press,date,price) values ('" + title + "', '" + score + "', '"
								+ rating_sum + "', '" + author + "', '" + press + "', '" + date + "', '" + price + "')";
						
						 Connection conn = null;
						 PreparedStatement pst = null;
						try {
							conn = JDBCUtil.getConnection();
							pst = conn.prepareStatement(sql);
							pst.execute();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							JDBCUtil.close(conn, pst);
						}
							
						//System.out.println(++count);
						
						// 睡眠防止ip被封
						try {
							System.out.println("睡眠2秒");
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						latch.countDown();
					}
					
				}
			});
			
			
			
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
		
	}
	
	
}



























