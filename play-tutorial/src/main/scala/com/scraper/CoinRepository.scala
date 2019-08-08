package com.scraper

import java.sql.Connection
import java.util.Calendar

import com.lucidchart.relate.{RowParser}
import javax.inject.Singleton

@Singleton
class CoinRepository extends Sql {

  override val tableName: String = "coins"

  private implicit val coinRowParser = RowParser { r =>
    Coin(
      id = r.int("id"),
      code = r.string("code"),
      price = r.double("price"),
      logTimestamp = r.long("logTimestamp")
    )
  }

  def create(input: Coin.Create)(implicit conn: Connection): Int = {

    val logTimestamp = Calendar.getInstance.getTimeInMillis

    val coinInsert = Coin.Insert(input, logTimestamp)

    val query =
      sql"""
           INSERT INTO ${tableName.toSql} (code, price, logTimestamp)
           VALUES (${coinInsert.code}, ${coinInsert.price}, ${coinInsert.logTimestamp})
         """
    query.executeInsertInt()
  }

}
