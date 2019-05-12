package com.bigdata.myproject.spark.dao

import com.bigdata.myproject.eshop.hbase.Untils.HbaseCnnetUtils
import com.bigdata.myproject.spark.domain.UserVistCount
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

object UserVistCountDao {

  val tableName = "page_vist_count"
  val cf ="info"
  val qulf = "page_vist_count"


  /**
    * 保存数据到Hbase中
   * @Param: list
  * @author xhy
  * @date 2019/5/11 12:39
  */
  def save(list:ListBuffer[UserVistCount]): Unit ={
    val htab = HbaseCnnetUtils.getInstance().getTab(tableName)
    for(ele <- list){
      htab.incrementColumnValue(ele.dayVist.getBytes(),
        cf.getBytes(),
        qulf.getBytes(),
        ele.pageVistCount
      )
    }
  }

  /**
    * 统计网页的访问次数
   * @Param: pageAdr
  * @author xhy
  * @date 2019/5/11 12:41
  */
  def count(pageAdr:String): Long ={
    val htab = HbaseCnnetUtils.getInstance().getTab(tableName)
    val get = new Get(pageAdr.getBytes());
    val count = htab.get(get).getValue(cf.getBytes(),qulf.getBytes())
    if (count == null){
      0l
    }
    else {
      Bytes.toLong(count)
    }
  }
}
