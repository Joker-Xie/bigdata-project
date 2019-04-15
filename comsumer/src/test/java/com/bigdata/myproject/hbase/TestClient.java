package com.bigdata.myproject.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;

public class TestClient {
    @Test
    public void write2Hbase() {
        try {
            Configuration myconf = HBaseConfiguration.create();
            //创建一个user表
//            HTableInterface userTable = new HTable(myconf,"user");
            //也可以创建表连接池
            HConnection conn = HConnectionManager.createConnection(myconf);
            HTableInterface userTable = conn.getTable("user");
            Put p = new Put(Bytes.toBytes("row1"));
            p.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("Lee"));
            p.add(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(5));
            p.add(Bytes.toBytes("info"), Bytes.toBytes("sex"), Bytes.toBytes("F"));
            userTable.put(p);
            Put p1 = new Put(Bytes.toBytes("row1"));
            p1.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("tomsLee"));
            p1.add(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(7));
            p1.add(Bytes.toBytes("info"), Bytes.toBytes("sex"), Bytes.toBytes("M"));
            userTable.put(p1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getData() {
        Configuration conf = HBaseConfiguration.create();
        try {
            HConnection conn = HConnectionManager.createConnection(conf);
            HTableInterface table = conn.getTable("user");
            Get g = new Get(Bytes.toBytes("row1"));
            Result rs = table.get(g);
            List<Cell> list =  rs.getColumnCells(Bytes.toBytes("info"),Bytes.toBytes("name"));
            byte[] a = list.get(0).getValue();
            System.out.println("a:"+Bytes.toString(a));
            byte[] b = list.get(1).getValue();
            System.out.println("b:"+Bytes.toString(b));

//            Map<byte[], byte[]> map = rs.getFamilyMap(Bytes.toBytes("info"));
//            for (byte[] key : map.keySet()) {
//                String nowKey = new String(key);
//                String val = null;
//                if (nowKey.equals("age")){
//                    val = map.get(key).toString();
//                }else {
//                    val = new String(map.get(key));
//                }
//                System.out.println("key:" + nowKey + " -> " + "value:" + val);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
