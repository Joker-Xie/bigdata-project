package com.bigdata.myproject.test.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class MyComsumer {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "192.168.31.129:9092");
        props.put("group.id", "test4");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //编写消费者
        KafkaConsumer consumer = new KafkaConsumer(props);
        Collection topic = new ArrayList();
        ((ArrayList) topic).add("test4");
        consumer.subscribe(topic);
        while (true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record :records){
                System.out.println(record.value());
            }
        }
    }
}
