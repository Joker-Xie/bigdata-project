package com.bigdata.myproject.eshop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import java.io.IOException;


public class Test {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HConnection conn = HConnectionManager.createConnection(conf);
        UserDao u = new UserDao(conn);
        u.addUser("LeeF","Huanghua","1235@byd.com","123456");
        System.out.println("Insert Over!");
    }
}
