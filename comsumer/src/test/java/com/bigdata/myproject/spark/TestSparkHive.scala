package com.bigdata.myproject.spark

import java.sql.DriverManager

object TestSparkHive {
  def main(args: Array[String]): Unit = {
    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val conn =DriverManager.getConnection("jdbc:hive2://192.168.31.128:10000")
    val st = conn.createStatement()
    st.execute("use eshop")
    val rs = st.executeQuery("select * from eshop.logs")
    while (rs.next()){
      println(rs.getString(1)+"  "+rs.getString(2))
    }
  }
}
