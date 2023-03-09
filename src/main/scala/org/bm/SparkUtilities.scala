package org.bm

import org.apache.spark.sql.{DataFrame, Encoder, SparkSession}
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import org.apache.spark.SparkConf

object SparkUtilities {

  def create_spark_session: SparkSession = {

    val datetime_pattern = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss")
      .withZone(ZoneId.systemDefault())
    val application_name = s"SS_for_unit_test_${datetime_pattern.format(Instant.now())}"
    val spark_config = Map(
      "spark.master" -> "local[*]",
      "spark.serializer.extraDebugInfo" -> "true",
      "spark.driver.host" -> "localhost"
    )
    val sparkConf = new SparkConf()
    spark_config.foreach { case (key, value) => sparkConf.set(key, value) }

    SparkSession
      .builder()
      .config(sparkConf)
      .appName(application_name)
      .getOrCreate()
  }

  //The main advantage of using a lazy val is that it defers the expensive
  // or time-consuming initialization of the value until it is actually needed.
  lazy val parent_spark_session: SparkSession = create_spark_session

}

// This trait is designed to create a new SparkSession for each instance that extends it.
// This can be useful in situations where you want to have multiple SparkSessions running concurrently,
// for example, when running tests or running multiple Spark jobs in parallel.
trait SparkUtilities {

  private var internal_spark_session: SparkSession = _

  private def create_session(): Unit = {
    internal_spark_session = SparkUtilities.parent_spark_session.newSession()
  }

  implicit protected def sparkSession: SparkSession = {
    if (internal_spark_session == null) {
      create_session()
    }
    internal_spark_session
  }

  def verify_dataframe_equality[A: Encoder](actual: DataFrame, expected: Seq[A]): Boolean = {
    val spark = sparkSession
    import spark.implicits._
    val actual_data = actual.as[A].collect().toSeq
    if (actual_data.size != expected.size ||
      actual_data.exists(i => !expected.contains(i)) ||
      expected.exists(i => !actual_data.contains(i))
    ) {
      false
    }
    true
  }

}
