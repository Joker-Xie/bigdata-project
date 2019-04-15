package com.bigdata.myproject.eshop.consumer;

import java.util.Timer;

/*
 * 多线程启动消费者
 * */
public class ConsumerApplication {
    public static void main(String[] args) {
        new Timer().schedule(new CloseMyFSDataStream(), 0, 30000);
        //hdfs消费者
        new Thread() {
            @Override
            public void run() {
                try {
                    HDFSRawConsumer consumer = new HDFSRawConsumer();
                    consumer.processLog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //hive消费者
        new Thread() {
            @Override
            public void run() {
                try {
                    HiveETLConsumer consumer = new HiveETLConsumer();
                    consumer.processLog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //hbase消费者
        new Thread() {
            @Override
            public void run() {
                try {
                    HbaseConsumer consumer = new HbaseConsumer();
                    consumer.processLog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
