package tianqiwang;

import java.io.IOException;

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
 * 或去某个区域所有月份的URL
 * @author wangshuang
 *
 */
public class UrlDownDate {

		public static final String Encoding="UTF-8";
		public static final String Webbase="http://lishi.tianqi.com/beijing/index.html";
		private final static HttpClient hc = new DefaultHttpClient();
		public static void main(String[] args) throws ClientProtocolException {
			
			HttpGet hg = new HttpGet(Webbase);
			//设置头标签
			hg.setHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36");
			HttpResponse response = null;
			HttpEntity entity = null;
			try {
				response = hc.execute(hg);
				entity = response.getEntity();
				String html = EntityUtils.toString(entity);
//				System.out.println(html);
				Document document = Jsoup.parse(html);
				
				Elements ul = document.select("div#tool_site>div.tqtongji1>ul");
				for(Element ele : ul){
					Elements li = ele.select("li");
					
					for(Element eles : li){
						Elements a = eles.select("a");
						String data = a.attr("href");//取出a标签中href的属性值
						System.out.println(data);
					}
					
				}
//				System.out.println(ul);
				EntityUtils.consume(entity);//释放资源
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}

}
