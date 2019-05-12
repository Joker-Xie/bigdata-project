package com.bigdata.myproject.spark.domain

/**
  * 网页访问次数的实体类
* @param dayVist: 每天访问rowkey
* @param pageVistCount:每天访问的次数
* @author xhy
* @date 2019/5/11 12:26
*/
case class UserVistCount (dayVist:String,pageVistCount:Int)
