package tianqiwang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
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

/**
 * 获取具体月份的数据 日期、最高温度、最低温度、风向、风力
 * 
 * @author wangshuang
 *
 */
public class CrawelDown {
	private static Configuration conf = null;
	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum",
				"192.168.1.61");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
	}


	public static final String encoding = "utf-8";
	public static final String base = "http://lishi.tianqi.com/yangjiang/";
	// public static final String Webbase =
	// "http://lishi.tianqi.com/yangjiang/201404.html";
	public static String Webbase = "";
	private final static HttpClient httpclient = new DefaultHttpClient();
	public static String FilePathName = "data/yangjiang/yangjiang.txt";

	
	public static List<WetherEntity> wetherAllData = new ArrayList<WetherEntity>();
	
	public static List<WetherEntity> crawel(String date1,String date2){
		
		List a = Util.getDate(date1, date2);
		for (int i = 0; i < a.size(); i++) {
			Webbase = "http://lishi.tianqi.com/yangjiang//" + a.get(i)
					+ ".html";

			// System.out.println(Webbase);

			HttpGet get = new HttpGet(Webbase);
			get.setHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36");
			HttpResponse response = null;
			HttpEntity entity = null;
			try {
				response = httpclient.execute(get);
				entity = response.getEntity();
				// System.out.println(EntityUtils.toString(entity));
				String html = EntityUtils.toString(entity);

				Document document = Jsoup.parse(html);
				// System.out.println(document.select("div#tool_site>div.tqtongji2>ul"));
				Elements ul = document.select("div#tool_site>div.tqtongji2>ul");

				
				String[] colname = {"tempmax","tempmin","weather","winddir","windpower"};
				// 第一种方法：去掉第一行 标题
				// for (int index=1; index < ul.size(); index++) {
				// Element ele = ul.get(index);
				// 第二种方法：去掉第一行的标题
				int index = 0;
				for (Element ele : ul) {
					if (index == 0) {
						index++;
						continue;
					}
					String data = null;
					Elements li = ele.select("li");
					String str = "";
					
					
					for (Element eles : li) {
						data = eles.text();

						str += data + ";";
					}
					
					String[] wetherdata = str.split(";");
					WetherEntity wetherEntity = new WetherEntity();
					wetherEntity.setDate(String.valueOf(wetherdata[0]));
					wetherEntity.setTempmax(String.valueOf(wetherdata[1]));
					wetherEntity.setTempmin(String.valueOf(wetherdata[2]));
					wetherEntity.setWether(String.valueOf(wetherdata[3]));
					wetherEntity.setWinddir(String.valueOf(wetherdata[4]));
					wetherEntity.setWindpower(String.valueOf(wetherdata[5]));
					wetherAllData.add(wetherEntity);
				
//					Util.appendData(FilePathName, str);
					// System.out.println();
					// CrawelDown.appendData(FilePathName, data);
				}

				EntityUtils.consume(entity);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return wetherAllData;
		
	}
	
	public static void main(String[] args){
		String tableName  = "weather";
		HTable table;
		try {
			table = new HTable(conf, tableName);
			String date1 = "201101";
			String date2 = "201102";
			List<WetherEntity> list=crawel(date1,date2);
			String[] colname = {"tempmax","tempmin","weather","winddir","windpower"};
			String rowname=list.get(0).getDate();
			for(int i=1;i<list.size()-1;i++){
//				System.out.println(list.get(i).getDate()+
//						list.get(i).getTempmax()+list.get(i).getTempmin()+
//						list.get(i).getWether()+list.get(i).getWinddir()+
//						list.get(i).getWindpower());
				String valueData=list.get(i).getTempmax()+list.get(i).getTempmin()+
						list.get(i).getWether()+list.get(i).getWinddir()+
						list.get(i).getWindpower();
				
				Put put = new Put(Bytes.toBytes(rowname));
				// 参数出分别：列族、列、值
				put.add(Bytes.toBytes("info"), Bytes.toBytes(colname[i-1]),
						Bytes.toBytes(valueData));
				table.put(put);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
//	public static void main(String[] args) {
//		// 实例HttpClient 并执行带有HttpGet的方法,返回HttpResponse 响应，再进行操作
//		String date1 = "201101";
//		String date2 = "201405";
//		List a = Util.getDate(date1, date2);
//		for (int i = 0; i < a.size(); i++) {
//			Webbase = "http://lishi.tianqi.com/yangjiang//" + a.get(i)
//					+ ".html";
//
//			// System.out.println(Webbase);
//
//			HttpGet get = new HttpGet(Webbase);
//			get.setHeader(
//					"User-Agent",
//					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36");
//			HttpResponse response = null;
//			HttpEntity entity = null;
//			try {
//				response = httpclient.execute(get);
//				entity = response.getEntity();
//				// System.out.println(EntityUtils.toString(entity));
//				String html = EntityUtils.toString(entity);
//
//				Document document = Jsoup.parse(html);
//				// System.out.println(document.select("div#tool_site>div.tqtongji2>ul"));
//				Elements ul = document.select("div#tool_site>div.tqtongji2>ul");
//
//				// 第一种方法：去掉第一行 标题
//				// for (int index=1; index < ul.size(); index++) {
//				// Element ele = ul.get(index);
//				// 第二种方法：去掉第一行的标题
//				int index = 0;
//				for (Element ele : ul) {
//					if (index == 0) {
//						index++;
//						continue;
//					}
//					// if
//					// if(ele.attr("t1")){
//					// continue;
//					// }
//					String data = null;
//					Elements li = ele.select("li");
//					String str = "";
//					for (Element eles : li) {
//						data = eles.text();
//
//						str += data + ";";
//
//						// System.out.print(data + " ");
//					}
//					Util.appendData(FilePathName, str);
//					// System.out.println();
//					// CrawelDown.appendData(FilePathName, data);
//				}
//
//				EntityUtils.consume(entity);
//
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
}