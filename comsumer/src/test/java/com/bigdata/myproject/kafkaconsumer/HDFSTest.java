package com.bigdata.myproject.kafkaconsumer;

import com.bigdata.myproject.eshop.consumer.StringUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class HDFSTest {
    @Test
    public void test(){
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            FSDataInputStream in = fs.open(new Path("/hadoop/1.txt"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copyBytes(in, baos, 1024);
            System.out.println(new String(baos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testLogUtils(){
        String log = "s201,192.168.31.1|||-|||05/Mar/2019:09:40:01 -0500|||GET /eshop/phone/note7.html HTTP/1.0|||200|||336|||-|||ApacheBench/2.3|||";
        String[]  arr = StringUtils.logSplit(log);
        String host = StringUtils.getHost(arr);
        String date = StringUtils.formatYyyymmddHHMMss(arr);
        System.out.println("xxxxx");
    }

}
