package com.bigdata.myproject.eshop.consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


public class HbaseWrite {
   private static Configuration conf = HBaseConfiguration.create();
   private static final  byte[] TABLE_NAME = Bytes.toBytes("weblogs");
   private static final  byte[] INFO = Bytes.toBytes("info");
   private static final  byte[] DATA = Bytes.toBytes("logs");

    public static void write(long rowKey,String log){
        try {
            HConnection conn = HConnectionManager.createConnection(conf);
            Put p = mkPut(rowKey,log);
            HTableInterface htable  = conn.getTable(TABLE_NAME);
            htable.put(p);
            htable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Put mkPut(long rowKey,String log){
        Put p = new Put(Bytes.toBytes(rowKey));
        p.add(INFO,DATA,Bytes.toBytes(log));
        return p;
    }
}
