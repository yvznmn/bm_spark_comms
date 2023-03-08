package org.bm

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import org.apache.spark.sql.SparkSession
import org.bm.SparkUtilities

@RunWith(classOf[JUnitRunner])
class SparkUtilitiesTests extends AnyFlatSpec with BeforeAndAfter {

  var spark: SparkSession = _

  before {
    val spark_config = Map("spark.master" -> "local[*]")
    val app_name = "test"
    spark = SparkUtilities.create_spark_session(app_name, spark_config)
  }

  after {
    spark.stop()
  }

  behavior of "create_spark_session function"
  it should "create a SparkSession with the expected configuration" in {
    val actual = spark.conf.get("spark.app.name").dropRight(3)
    val datetime_pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
    val expected = s"test_${datetime_pattern.format(Instant.now())}".dropRight(3)

    actual shouldBe expected
  }

  it should "return a SparkSession object" in {
    val actual = spark.isInstanceOf[SparkSession]
    val expected = true

    actual shouldBe expected
  }


}
