package com.bigdata.myproject.spark.comsumer

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TestSparkStreamingBroadcast {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("Kafka&Spark")
    val ssc = new StreamingContext(conf, Seconds(3))
    //编写kafka配置文件
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "s202:9092,s203:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "myspark_kafka",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val topics = Array("test4")
    //
    val kafkaStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    kafkaStream.map(record => {
      val value = record.value()
      println("value: " +value)
      val ac = changeMsg(value)
      val sendTopic = "test"
      val producer  = mkProducer()
      producer.send(new ProducerRecord[String,String](sendTopic,value,ac))
      println(ac)
    }).print()
    ssc.start()
    ssc.awaitTermination()
  }
  def changeMsg(msg: String): String = {
    if (msg.contains("hello"))
      "how are you !" else "see you"
  }
  def mkProducer(): KafkaProducer[String,String] ={
    val KafkaConfs = new Properties()
    KafkaConfs.put("bootstrap.servers","s202:9092,s203:9092")
    KafkaConfs.put("key.serializer",classOf[StringSerializer])
    KafkaConfs.put("value.serializer",classOf[StringSerializer])
    new KafkaProducer[String, String](KafkaConfs)
  }

}
