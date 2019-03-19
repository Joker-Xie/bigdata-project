package com.bigdata.myproject.eshop.consumer;

import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.IOException;

public class MyFSDataOutputStream extends FSDataOutputStream {
    private HDFSRawStreamPool pool;
    private FSDataOutputStream out ;
    private String path;
    public MyFSDataOutputStream(FSDataOutputStream out,String path,HDFSRawStreamPool pool) throws Exception{
        super(null);
        this.pool = pool;
        this.out = out;
    }

    @Override
    public void close() throws IOException {
        pool.putBack(path,this);
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    @Override
    public void hflush() throws IOException {
        out.hflush();
    }
}
