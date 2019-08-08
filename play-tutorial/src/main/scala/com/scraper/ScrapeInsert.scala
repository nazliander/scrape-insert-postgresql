package com.scraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import play.api.ApplicationLoader.Context
import play.api.Environment
import play.api.db.Database
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationLoader

object ScrapeInsert {

  def main(args: Array[String]): Unit = {

    def retrieveInjector(): Injector = {
      val environment = Environment.simple()
      val context = Context.create(environment = environment)
      new GuiceApplicationLoader().load(context).injector
    }

    val injector = retrieveInjector()
    val database = injector.instanceOf[Database]
    val coinService = injector.instanceOf[CoinService]

    val coinTable =
      getCoinUpdatedTable("https://coinmarketcap.com", "currencies")

    println("Query dump started.")

    coinTable
      .map { coin =>
        database.withConnection { implicit conn =>
          coinService.add(coin)
        }
      }

    println("Query dumped.")

  }

  def siteConnect(html: String, browser: JsoupBrowser): browser.DocumentType = {
    browser.get(html)
  }

  def getCoinUpdatedTable(homepage: String,
                          tableNameInHTML: String): Vector[Coin.Create] = {
    val site = siteConnect(homepage, new JsoupBrowser())

    val tab = site >> table(s"#${tableNameInHTML}")

    val body = tab.slice(1, tab.length)

    body.map(x => Coin.Create(x.map(_.text).toList))
  }
}
