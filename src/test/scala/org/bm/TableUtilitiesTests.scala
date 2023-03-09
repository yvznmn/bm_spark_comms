package org.bm

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.junit.JUnitRunner
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import org.bm.TableUtilities
import org.apache.spark.sql.{SparkSession, Row}
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.types.{StructField, StructType, IntegerType, StringType}



@RunWith(classOf[JUnitRunner])
class TableUtilitiesTests extends TableUtilities {

  behavior of "create_delta_table function"
  it should "create a Delta table in the specified path for the given DataFrame with the specified name" in {

    val test_schema = StructType(Seq(
      StructField("id", IntegerType),
      StructField("name", StringType),
      StructField("age", IntegerType)
    ))

    val test_data = Seq(
      (1, "Alice", 25),
      (2, "Bob", 30),
      (3, "Charlie", 35)
    )
//
//    val spark_config = Map("spark.master" -> "local[*]")
//    val app_name = "DeltaTableCreator"
//    val spark = SparkUtilities.create_spark_session

    import spark.implicits._
    val df = spark.createDataFrame(test_data.toDF.rdd, test_schema)
    val table_name = "testDeltaTable"
    val table_path = s"C://spark_temp/${table_name}/"

    val deltaTable = create_delta_table(df, table_name, table_path)

    deltaTable.toDF.show()

    deltaTable.toDF.count().toInt should be (3)
    deltaTable.toDF.schema.fields.map(_.name) should be (Seq("id", "name", "age"))
//    deltaTable.toDF.agg(sum("age")).head().getInt(0) should be (90)

  }


}
