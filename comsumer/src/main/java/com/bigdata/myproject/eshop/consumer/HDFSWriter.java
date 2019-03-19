package com.bigdata.myproject.eshop.consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/*
 * hdfs写入器
 * */
public class HDFSWriter {
    /*
     * 数据写入hdfs中
     * hdfs://mycluster/usr/log/2019/03/13/s201.log
     * */
    private String prePath = "";
    private FSDataOutputStream out = null;
    public void writeLog2HDFS(String path, String log) {
        try {
            if (!prePath.equals(path)) {
                if (out != null) {
                    out.close();
                }else {
                    out = HDFSRawStreamPool.getInstance().getInputStream(path);
                }
            } else {
                if (out == null) {
                    out = HDFSRawStreamPool.getInstance().getInputStream(path);
                }
            }
            prePath = path;
            out.write(log.getBytes());
            //由于flume收集数据不存在分行现象，需要手动分行
            out.write("\r\n".getBytes());
            out.hflush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
