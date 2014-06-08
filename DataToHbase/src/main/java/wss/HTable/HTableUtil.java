package wss.HTable;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class HTableUtil {
	 private static HTable table;
     private static Configuration conf;
     
     static{
         	conf = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum",
					"192.168.1.61");
			conf.set("hbase.zookeeper.property.clientPort", "2181");
          
             try {
                     table = new HTable(conf,"testtable");
             } catch (IOException e) {
                     e.printStackTrace();
             }
     }
     
     public static Configuration getConf(){
             return conf;
     }
     
     public static HTable getHTable(String tablename){
             if(table==null){
                     try {
                             table= new HTable(conf,tablename);
                     } catch (IOException e) {
                             e.printStackTrace();
                     } 
             }
             return table;
     }
     
     public static  byte[] gB(String name){
             return Bytes.toBytes(name);
     }
}


