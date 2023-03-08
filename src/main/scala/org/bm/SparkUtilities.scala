package org.bm

import org.apache.spark.sql.SparkSession
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import org.apache.spark.SparkConf

object SparkUtilities {

  def create_spark_session(app_name: String, spark_config: Map[String, String]): SparkSession = {

    val datetime_pattern = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss")
      .withZone(ZoneId.systemDefault())
    val application_name = s"${app_name}_${datetime_pattern.format(Instant.now())}"
    val sparkConf = new SparkConf()
    spark_config.foreach { case (key, value) => sparkConf.set(key, value) }

    SparkSession
      .builder()
      .config(sparkConf)
      .appName(application_name)
      .getOrCreate()

  }

}
