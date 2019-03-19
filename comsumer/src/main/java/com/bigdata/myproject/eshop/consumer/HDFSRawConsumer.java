package com.bigdata.myproject.eshop.consumer;


import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class HDFSRawConsumer {
    private final KafkaConsumer consumer;
    private final Collection topic = new ArrayList() ;
    private final HDFSWriter writer = new HDFSWriter();

    public HDFSRawConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.31.129:9092");
        props.put("group.id", "g2");
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
                String[]  arr = StringUtils.logSplit(log);
                if (arr == null || arr.length < 7){
                    continue;
                }
                String host = StringUtils.getHost(arr);
                String date = StringUtils.formatYyyymmddHHMMss(arr);
                String path = "/user/centos/eshop/raw/"+date+"/"+host+".log";
                writer.writeLog2HDFS(path,log);
                System.out.println("ok");
            }
        }
    }

    public static void main(String[] args) {
        HDFSRawConsumer consumer = new HDFSRawConsumer();
        consumer.processLog();
    }
}
