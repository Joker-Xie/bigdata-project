package com.bigdata.myproject.eshop.consumer;

/*
 * hdfs写入器
 * */
public class HDFSWriter {
    /*
     * 数据写入hdfs中
     * hdfs://mycluster/usr/log/2019/03/13/s201.log
     * */
    private String prePath = "";
    private MyFSDataOutputStream out = null;

    public void writeLog2HDFS(String path, String log) {
        try {
            if (!prePath.equals(path)) {
                if (out != null) {
                    out.release();
                    out = null;
                }
                out =(MyFSDataOutputStream) HDFSRawStreamPool.getInstance().takeOutputStream(path);
                prePath = path;
            }
            out.write(log.getBytes());
            //由于flume收集数据不存在分行现象，需要手动分行
            out.write("\r\n".getBytes());
            out.hsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
