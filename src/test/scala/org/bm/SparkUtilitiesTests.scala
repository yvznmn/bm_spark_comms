package org.bm

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.bm.SparkUtilities

@RunWith(classOf[JUnitRunner])
class SparkUtilitiesTests extends DataframeUtilities {

  var spark_utils: SparkUtilities = _

  behavior of "create_spark_session function"
  it should "create a SparkSession with the expected configuration" in {
    val actual = spark.conf.get("spark.app.name").dropRight(3)
    val datetime_pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
    val expected = s"SS_for_unit_test_${datetime_pattern.format(Instant.now())}".dropRight(3)

    actual shouldBe expected
  }

  it should "return a SparkSession object" in {
    val actual = spark.isInstanceOf[SparkSession]
    val expected = true

    actual shouldBe expected
  }
}
