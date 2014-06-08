package wss.mysqltohbase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
/**
 * 通过客户端程序导入MySql数据
 * @author shuangsw
 *
 */
public class Recipe1 {

	/*
	 * 连接指定的HBase表
	 */
	private static HTable connectHBase(String tablename) throws IOException {
		HTable table = null;
		Configuration conf = HBaseConfiguration.create();
		table = new HTable(conf, tablename);
		return table;
	}

	/*
	 * 连接MySql
	 */
	private static Connection connectDB() {
		String userName = "root";
		String password = "root";
		Connection conn = null;
		String url = "jdbc:mysql://192.168.1.60:9000/database=weather";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}

	public static void main(String[] args) {
		Connection dbConn = null;
		HTable htable = null;
		Statement stmt = null;
		String query = "select * from wether";
		dbConn = connectDB();
		try {
			htable = connectHBase("wether");
			byte[] family = Bytes.toBytes("n");
			stmt = dbConn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			long ts = System.currentTimeMillis();
			while (rs.next()) {
				String id = rs.getString("id");
				String date = rs.getString("date");
				String tempmax = rs.getString("tempmax");
				String tempmin = rs.getString("tempmin");
				String wether = rs.getString("wether");
				String winddir = rs.getString("winddir");
				String windpower = rs.getString("windpower");
				Put p = new Put(Bytes.toBytes(date));
				p.add(family, Bytes.toBytes(tempmax), Bytes.toBytes(tempmax));
				p.add(family, Bytes.toBytes(tempmin), Bytes.toBytes(tempmin));
				p.add(family, Bytes.toBytes(wether), Bytes.toBytes(wether));
				p.add(family, Bytes.toBytes(winddir), Bytes.toBytes(winddir));
				p.add(family, Bytes.toBytes(windpower),Bytes.toBytes(windpower));
				htable.put(p);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				if(stmt!=null){
				stmt.close();
				
				if(dbConn!=null){
					dbConn.close();
					}
				if(htable!=null){
					htable.close();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}

}
