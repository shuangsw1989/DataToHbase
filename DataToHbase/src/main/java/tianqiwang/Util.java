package tianqiwang;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Util {
	public static String Webbase ="";
/**
 * 传入两个时间段，获取之间的所有时间
 * @param date1
 * @param date2
 * @return
 */
	public static List getDate(String date1,String date2){
		List list = new ArrayList();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(format.parse(date1));
            end.setTime(format.parse(date2));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(start.before(end))
        {
        	list.add(format.format(start.getTime()));
//            System.out.println(format.format(start.getTime()));
            start.add(Calendar.MONTH,1);
        }
		return list;
	}
	/**
	 * 写入文本并追加数据
	 * @param filePathName
	 * @param fileContent
	 */
	public static void appendData(String filePathName, String fileContent) {
        try {
            File myFilePath = new File(filePathName.toString());
            if (!myFilePath.exists()) { // 如果该文件不存在,则创建
                myFilePath.createNewFile();
            }
            // FileWriter(myFilePath, true); 实现不覆盖追加到文件里
             //FileWriter(myFilePath); 覆盖掉原来的内容
            FileWriter resultFile = new FileWriter(myFilePath, true);
            PrintWriter myFile = new PrintWriter(resultFile);
            // 给文件里面写内容,原来的会覆盖掉
            myFile.println(fileContent);
            resultFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args){
		String date1 ="201101";
		String date2 = "201102";
		List a = Util.getDate(date1, date2);
		for(int i=0;i<a.size();i++){
			Webbase = "http://lishi.tianqi.com/yangjiang//"+a.get(i)+".html";
			
			System.out.println(Webbase);
	}
	

		
		
	}
}
