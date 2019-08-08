package com.scrape

import java.util.Calendar

import cats.effect.IO
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import doobie.util.update.Update0
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

object ScrapeInsert extends App {

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql://localhost:5432/dev", // connect URL (driver-specific)
    "admin", // user
    "admin", // password
    ExecutionContexts.synchronous // just for testing
  )

  val coinTable =
    getCoinUpdatedTable("https://coinmarketcap.com", "currencies")

  coinTable
    .map { coinCreate =>
      val logTimestamp = Calendar.getInstance.getTimeInMillis
      val coinInsert = CoinInsert(coinCreate, logTimestamp)
      insertCoin(coinInsert).run.transact(xa).unsafeRunSync
    }

  def insertCoin(coinInsert: CoinInsert): Update0 =
    sql"""
           INSERT INTO coins (code, price, logTimestamp)
           VALUES (${coinInsert.code}, ${coinInsert.price}, ${coinInsert.logTimestamp})
      """.update

  def siteConnect(html: String, browser: JsoupBrowser): browser.DocumentType = {
    browser.get(html)
  }

  def getCoinUpdatedTable(webPage: String,
                          tableNameInHTML: String): Vector[CoinCreate] = {
    val site = siteConnect(webPage, new JsoupBrowser()) // Connects to the webpage.

    val tab = site >> table(s"#${tableNameInHTML}") // Gets the table with the given name.

    val body = tab.slice(1, tab.length) // First index belongs to the header of the table.

    body.map(x => CoinCreate(x.map(_.text).toList)) // Table rows are mapped to CoinCreate case class.
  }

}
