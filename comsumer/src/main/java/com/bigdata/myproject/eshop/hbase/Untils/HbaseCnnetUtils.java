package com.bigdata.myproject.eshop.hbase.Untils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;

import java.io.IOException;
import java.io.InterruptedIOException;

public class HbaseCnnetUtils {
    private Configuration conf;
    private HBaseAdmin admin;
    private HbaseCnnetUtils() {
        conf = HBaseConfiguration.create();
        try {
            admin = new HBaseAdmin(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static HbaseCnnetUtils instance = null;
    public static HbaseCnnetUtils getInstance(){
        if (instance == null){
            instance = new HbaseCnnetUtils();
        }
        return instance;
    }


    public HTable getTab(String tableName){
        HTable table = null ;
        try {
            table = new HTable(conf,tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * @Param: tableName    Hbase 表名
    * @Param: rowkey        Hbase rowkey
    * @Param: cf            Hbase 列簇
    * @Param: value         输入值
    */
    public void put(String tableName,String rowkey,String cf ,String cloum,String value){
        HTable table = getTab(tableName);
        Put put = new Put(rowkey.getBytes());
        put.add(cf.getBytes(),cloum.getBytes(),value.getBytes());
        try {
            table.put(put);
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        } catch (RetriesExhaustedWithDetailsException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       HbaseCnnetUtils.getInstance().put("page_vist_count","20190511","info","page_vist_count","20");
//        System.out.println(tab.getName().getNameAsString());
    }

}
