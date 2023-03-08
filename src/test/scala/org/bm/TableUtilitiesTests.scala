package org.bm

import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TableUtilitiesTests extends AnyFlatSpec with BeforeAndAfter {

  behavior of "create_delta_table function"
  it should "create a Delta table in the specified path for the given DataFrame with the specified name" in {

    val spark_config = Map("spark.master" -> "local[*]")
    val app_name = "DeltaTableCreator"
    val spark = SparkUtilities.create_spark_session(app_name, spark_config)

    import spark.implicits._
    val test_data = Seq(
      (1,"John"),
      (2,"Jane"),
      (3,"Jim")
    ).toDF("id", "name")
    val table_name = "employees"
    val table_path = ""

  }


}
