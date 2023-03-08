package org.bm

import io.delta.tables.DeltaTable
import org.apache.spark.sql.{DataFrame, SaveMode}

object TableUtilities {

  def create_delta_table(df: DataFrame, table_name: String, table_path: String): DeltaTable = {

    df.write.format("delta").mode(SaveMode.Overwrite).option("path", table_path).save(table_name)

    DeltaTable
      .createOrReplace(df.sparkSession)
      .tableName(table_name)
      .addColumns(df.schema)
      .location(table_path)
      .execute()

    DeltaTable.forName(table_name)

  }

}
