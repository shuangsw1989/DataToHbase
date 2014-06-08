package wss.HTable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wss.DataToHbase.OperateTable;

public class CreateTable {

	public static void main(String[] args) {
		Logger.getRootLogger().setLevel(Level.WARN);
		try {
			String tableName = "weather";

			// 第一步：创建数据库表：“users2”
			String[] columnFamilys = { "info"};
			OperateTable.createTable(tableName, columnFamilys);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
