package com.bigdata.myproject.spark

import com.bigdata.myproject.spark.dao.UserVistCountDao
import com.bigdata.myproject.spark.domain.{CleanLogs, UserVistCount}
import com.bigdata.myproject.spark.utils.DateUtils
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

/*
* 使用spark streaming 消费kafka数据，统计出不同手机总共访问次数
* */
object SparkStreamingConsumer {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkStreamingConsumer")
    val ssc = new StreamingContext(conf, Seconds(60))
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "s202:9092,s203:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "spark_consumer",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val topics = Array("eshop")
    //拿到kafka流
    val kafkaStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    /*
    * s202,192.168.31.1|||-|||02/Apr/2019:17:14:54 +0800|||GET /eshop/phone/note7.html HTTP/1.0|||200|||336|||-|||ApacheBench/2.3|||-
    *     ==> note7
    *        ==>count()
    * */
    //解析数据
    val streamSet = kafkaStream.map(_.value())
    val cleanData = streamSet.map(
      line => {
        val infos = line.replace("|||", ",").split(",")
        //获取主机号
        if (infos.size > 1) {
          val hostName = infos(0)
          //获取时间
          val dateTime = DateUtils.parse2Minue(infos(3).split(" ")(0))
          //获取连接地址
          val httpAdr = infos(4).split(" ")(1)
          CleanLogs(hostName, dateTime, httpAdr)
        }
        else {
          null
        }
      }
      //filter操作具体到集合或参数
    ).filter(cleanLog => (cleanLog !=null))
    //使用SparkStreaming将数据写入hbase中
      cleanData.map(
        x => (x.dateTime + "_" + x.httpAdr, 1)
      ).reduceByKey(_ + _).foreachRDD(
        rdd => {
          rdd.foreachPartition(
            pattions => {
              val list = new ListBuffer[UserVistCount]
              pattions.foreach(
                part => {
                  list.append(UserVistCount(part._1, part._2))
                }
              )
              UserVistCountDao.save(list)
            }
          )
        }
      )
    cleanData.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
