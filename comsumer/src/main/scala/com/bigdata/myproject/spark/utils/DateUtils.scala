package com.bigdata.myproject.spark.utils

import java.util.{Date, Locale}

import org.apache.commons.lang3.time.FastDateFormat

object DateUtils {
  //s204,192.168.31.1|||-|||09/May/2019:23:12:45 +0800|||GET /eshop/phone/note7.html HTTP/1.0|||200|||336|||-|||ApacheBench/2.3|||-
  val ddMMMYYYYHHmmss_Format = FastDateFormat.getInstance("dd/MMM/YYYY:HH:mm:ss", Locale.US)
  val Target_Format = FastDateFormat.getInstance("YYYYMMddHHmmss")

  def getTime(time:String) ={
    ddMMMYYYYHHmmss_Format.parse(time).getTime
  }
  def parse2Minue(time:String)={
    Target_Format.format(new Date(getTime(time)))
  }
}
