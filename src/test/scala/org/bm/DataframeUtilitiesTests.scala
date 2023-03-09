package org.bm

import org.junit.runner.RunWith
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DataframeUtilitiesTests extends DataframeUtilities {

  behavior of "verify_dataframe_equality"
  it should "return true when the actual DataFrame matches the expected data" in {

    import spark.implicits._

    val expected_data = Seq(
      TestClass("foo"),
      TestClass("bar")
    )

    val actual = spark.createDataFrame(expected_data).toDF
    val result = verify_dataframe_equality(actual, expected_data)
    val expected = true

    result shouldBe expected

  }

  it should "return false when the actual DataFrame does NOT matches the expected data" in {

    import spark.implicits._

    val actual_data = Seq(
      TestClass("foo"),
      TestClass("bar")
    )
    val expected_data = Seq(
      TestClass("fooo"),
      TestClass("barr")
    )

    val actual = spark.createDataFrame(actual_data).toDF
    val result = verify_dataframe_equality(actual, expected_data)
    val expected = false

    result shouldBe expected

  }

}
