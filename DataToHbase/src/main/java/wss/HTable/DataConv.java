package wss.HTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataConv {
	// 存储文件中的数据
	 

	public List<String> readTxtFile(String filePath){
		List<String> lists = new ArrayList<String>();
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中
					lists = Arrays.asList(array);
					for(String s :lists){
						lists.add(s);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
}
		return lists;
	}

	private String[] split(String lineTxt) {
		String[] array = lineTxt.split(";");//分号分割字符串
		return array;
	}
}
