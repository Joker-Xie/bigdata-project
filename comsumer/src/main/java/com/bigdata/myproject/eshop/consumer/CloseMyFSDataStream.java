package com.bigdata.myproject.eshop.consumer;



import java.util.TimerTask;

public class CloseMyFSDataStream extends TimerTask {
    @Override
    public void run() {
        HDFSRawStreamPool pool = HDFSRawStreamPool.getInstance();
        pool.releasePool();
    }
}
