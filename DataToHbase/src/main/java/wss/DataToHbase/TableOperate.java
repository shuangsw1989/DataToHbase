package wss.DataToHbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class TableOperate {

	// 声明静态配置
	private static Configuration configuration = null;
	static {
		configuration = HBaseConfiguration.create();//创建所需的配置
		configuration.set("hbase.zookeeper.quorum", "192.168.1.61");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
	}
	
	
	 /** 
     * 创建表 
     *  
     * @param tableName 
     */  
    public static void createTable(String tableName) {  
        System.out.println("start create table ......");  
        try {  
            HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);  
            if (hBaseAdmin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建  
                hBaseAdmin.disableTable(tableName);  
                hBaseAdmin.deleteTable(tableName);  
                System.out.println(tableName + " is exist,detele....");  
            }  
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);  
            tableDescriptor.addFamily(new HColumnDescriptor("temperature"));  
            tableDescriptor.addFamily(new HColumnDescriptor("wind"));  
            tableDescriptor.addFamily(new HColumnDescriptor("wether"));  
            hBaseAdmin.createTable(tableDescriptor);  
        } catch (MasterNotRunningException e) {  
            e.printStackTrace();  
        } catch (ZooKeeperConnectionException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        System.out.println("end create table ......");  
    }  
	
    
	public static void main(String[] args) {
//		控制日志输出
//		http://stackoverflow.com/questions/16738364/how-can-i-suppress-info-logs-in-an-hbase-client-application
		Logger.getLogger("org.apache.zookeeper").setLevel(Level.WARN);
		Logger.getLogger("org.apache.hadoop.hbase.zookeeper").setLevel(Level.WARN);
		Logger.getLogger("org.apache.hadoop.hbase.client").setLevel(Level.WARN);
//		Or just simply manipulate the rootlogger:
		Logger.getRootLogger().setLevel(Level.WARN);
		
		String tableName="t_wether";
		TableOperate.createTable(tableName);

	}

}
