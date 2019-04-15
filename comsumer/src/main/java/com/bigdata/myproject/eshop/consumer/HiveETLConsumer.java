package com.bigdata.myproject.eshop.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Timer;

public class HiveETLConsumer {
    private final KafkaConsumer consumer;
    private final Collection topic = new ArrayList();
    private final HDFSWriter writer = new HDFSWriter();

    public HiveETLConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.31.129:9092");
        props.put("group.id", "g1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer(props);
    }

    public void processLog() {
        ((ArrayList) topic).add("eshop");
        consumer.subscribe(topic);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String log = record.value();
                String[] arr = StringUtils.logSplit(log);
                if (arr == null || arr.length < 7) {
                    continue;
                }
                //进行数据清洗
                String[] strs = StringUtils.logETL(log);
                if (!strs[3].endsWith("html")) {
                    continue;
                }
                System.out.println("hive: " + log);
                String host = StringUtils.getHost(strs);
                String[] explodeDate = StringUtils.explodeDate(strs);
                log = StringUtils.arr2Str(strs);
                String path = "/user/centos/eshop/clear/"
                        + explodeDate[0]
                        + "/" + explodeDate[1]
                        + "/" + explodeDate[2]
                        + "/" + explodeDate[3]
                        + "/" + explodeDate[4]
                        + "/" + host + ".log";
                writer.writeLog2HDFS(path, log);
            }
        }
    }
}
