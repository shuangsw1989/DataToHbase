package tianqiwang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFor {

	public static void main(String[] args) {
//		String s1 = "2011-02-01";
//		String s2 = "2012-04-04";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
//		Date begin;
//		try {
//			begin = sdf.parse(s1);
//			Date   end=sdf.parse(s2);      
//			double   between=(end.getTime()-begin.getTime())/1000;//����1000��Ϊ��ת������      
//			double  day=between/(24*3600);
//			for(int i = 1;i<=day;i++){
//
//				Calendar cd = Calendar.getInstance();   
//	        cd.setTime(sdf.parse(s1));   
//	        cd.add(Calendar.DATE, i);//����һ��   
//	        //cd.add(Calendar.MONTH, n);//����һ����
//	        System.out.println(sdf.format(cd.getTime()));
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}      
//		
		
//		/*
//		 * 
//		 */
//		String str="20110214";
//        String str1="20120225";
//        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
//        Calendar start = Calendar.getInstance();
//        Calendar end = Calendar.getInstance();
//        try {
//            start.setTime(format.parse(str));
//            end.setTime(format.parse(str1));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        while(start.before(end))
//        {
//            System.out.println(format.format(start.getTime()));
//            start.add(Calendar.DAY_OF_MONTH,1);
//        }
		
		
		/*
		 * 
		 */
		String str="201102";
        String str1="201202";
        SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(format.parse(str));
            end.setTime(format.parse(str1));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(start.before(end))
        {
            System.out.println(format.format(start.getTime()));
            start.add(Calendar.MONTH,1);
        }
		
		
		/*
		 * 
		 */
//		String date1 ="201101";
//		String date2 ="201404";
//		SimpleDateFormat format = new SimpleDateFormat("yyyymm");
//	
//		Calendar start = Calendar.getInstance();
//		Calendar end = Calendar.getInstance();
//		try {
//			start.setTime(format.parse(date1));
//			end.setTime(format.parse(date2));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(start.before(end))
//		{
//				for(int i=1;i<=12;i++){
//					start.add(Calendar.MONTH, i);
//					System.out.println(format.format(start.getTime()));
//				}	
//			
//	
//		
//		}
//		
	}

}
