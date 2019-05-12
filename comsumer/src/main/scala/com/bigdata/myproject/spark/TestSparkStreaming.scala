package com.bigdata.myproject.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TestSparkStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkTest").setMaster("local[4]")
    val stream = new StreamingContext(conf,Seconds(3))
    val ss = stream.socketTextStream("localhost",9999).flatMap(_.split(" ")).map(a => (a,1))
    .reduceByKeyAndWindow((a:Int,b:Int) =>{a+b},Seconds(6),Seconds(6))
    ss.print()
    stream.start()
    stream.awaitTermination()
  }
}
