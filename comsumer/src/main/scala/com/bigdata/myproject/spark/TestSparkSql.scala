package com.bigdata.myproject.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object TestSparkSql {
  case class user(name:String , age:Int, occ:String)
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("sparkSqlTest")
    val sc = new SparkContext(conf);
    val session = SparkSession.builder().getOrCreate()
    import session.implicits._
    val value = Array[String]("lee,15,student","json,16,techer","joden,30,player")
    def toUser(arg:Seq[String]):user =user(arg(0),arg(1).toInt,arg(2))
    val df = sc.makeRDD(value).map(
      a => {
        val w  = a.split(",")
        toUser(w)
      }
    ).toDF().createOrReplaceTempView("user")
    val ss = session.sql("select * from user where age >16")
    ss.foreach( x => println(
      x.getString(0)+"-"+x.getInt(1)+"-"+x.getString(2)
    ))
  }
}
