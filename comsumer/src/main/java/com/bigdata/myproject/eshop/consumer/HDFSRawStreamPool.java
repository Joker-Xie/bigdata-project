package com.bigdata.myproject.eshop.consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HDFSRawStreamPool {
    private static FileSystem fs;
    //连接池
    private static Map<String, FSDataOutputStream> pool = new HashMap<String, FSDataOutputStream>();
    //单例模式生成池
    private static HDFSRawStreamPool instance;

    public static HDFSRawStreamPool getInstance() {
        if (instance == null) {
            instance = new HDFSRawStreamPool();
        }
        return instance;
    }

    private HDFSRawStreamPool() {
        try {
            Configuration conf = new Configuration();
            fs = FileSystem.get(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized FSDataOutputStream takeOutputStream(String path) {
        try {
            FSDataOutputStream out = pool.remove(path);
            Path p = new Path(path);
            //
            /*
            * 判断是不是有流存在
            * 注意，生产环境下不要使用append,append存在不稳定性，特别是是在池化模式下
            * */
            if (out == null) {
                out = fs.create(p);
            }
            return new MyFSDataOutputStream(out, path, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //回收流
    public synchronized void putBack(String path, FSDataOutputStream out) {
        pool.put(path, out);
    }

    //释放流
    public synchronized void releasePool() {
        try {
            for (FSDataOutputStream o : pool.values()) {
                o.close();
            }
            pool.clear();
            System.out.println("释放池子！！！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
