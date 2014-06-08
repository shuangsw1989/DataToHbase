package wss.lecai;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tianqiwang.Util;

public class lecai {
//	 public static final String Webbase =
//	 "http://baidu.lecai.com/lottery/draw/list/50?d=2014-01-01";
	private final static HttpClient httpclient = new DefaultHttpClient();
	public static String FilePathName = "src/main/resources/data/leicai.txt";
	
	public static void main(String[] args){
		for(int i =2014;i>2002;i--){
			String url = "http://baidu.lecai.com/lottery/draw/list/50?d="+i+"-01-01";
//			System.out.println(url);
			getLecai(url);
		}
		
	}
	
	public static void getLecai(String url) {

		
			HttpGet get = new HttpGet(url);
			get.setHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36");
			HttpResponse response = null;
			HttpEntity entity = null;
			try {
				response = httpclient.execute(get);
				entity = response.getEntity();
//				 System.out.println(EntityUtils.toString(entity));
				String html = EntityUtils.toString(entity);

				Document document = Jsoup.parse(html);
//				System.out.println(document.select("html>body>div.main"));
//				 System.out.println(document.select("html>body>div.main>div.right_box>div.right_main>div>table#draw_list>tbody"));
				Elements tableTrs = document.select("html>body>div.main>div.right_box>div.right_main>div>table#draw_list>tbody>tr");

				// 第一种方法：去掉第一行 标题
				// for (int index=1; index < ul.size(); index++) {
				// Element ele = ul.get(index);
				// 第二种方法：去掉第一行的标题
				int index = 0;
				for (Element tr : tableTrs) {
					String data = "";
					Elements tableTds = tr.select("td");
					String str = "";
					for (Element td : tableTds) {
						data = td.text();
						str += data + " ";
					}
//					System.out.println(str);
					Util.appendData(FilePathName, str);
				}

				EntityUtils.consume(entity);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch blockc
				e.printStackTrace();
			}
		}
	}

