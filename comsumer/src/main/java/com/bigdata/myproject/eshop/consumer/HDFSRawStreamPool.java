package com.bigdata.myproject.eshop.consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

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

    public synchronized FSDataOutputStream getInputStream(String path) {
        try {
            //判断是不是有流存在
            FSDataOutputStream out = pool.remove(path);
            if (out == null) {
                System.out.println(path);
                Path p = new Path(path);
                if (!fs.exists(p)) {
                    fs.createNewFile(p);
                }
                out = fs.append(p);
            }
            return new MyFSDataOutputStream(out, path, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putBack(String path, FSDataOutputStream out) {
        pool.put(path, out);
    }
}
