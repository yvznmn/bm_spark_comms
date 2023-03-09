package org.bm

import org.apache.spark.sql.{DataFrame, Encoder}
import org.scalatest.flatspec.AnyFlatSpec

abstract class DataframeUtilities extends AnyFlatSpec with SparkUtilities {

  val spark = sparkSession

  def verify_dataframe_equality[A: Encoder](actual: DataFrame, expected: Seq[A]): Boolean = {

    import spark.implicits._

    val actual_data = actual.as[A].collect().toSeq
    if (actual_data.size != expected.size ||
      actual_data.exists(i => !expected.contains(i)) ||
      expected.exists(i => !actual_data.contains(i))
    ) false else true
  }

}

