package com.scraper

import java.sql.Timestamp

case class Coin(id: Int, code: String, price: Double, logTimestamp: Long)

object Coin {
  case class Create(code: String, price: Double)

  object Create {
    def apply(listOfElements: List[String]): Coin.Create = {
      Coin.Create(
        code = getCoinCode(listOfElements(1)),
        price = numberStringToDouble(listOfElements(3))
      )
    }
  }

  case class Insert(code: String, price: Double, logTimestamp: Timestamp)

  object Insert {
    def apply(coin: Coin.Create, logTimestamp: Long): Coin.Insert = {
      Coin.Insert(
        code = coin.code,
        price = coin.price,
        logTimestamp = new Timestamp(logTimestamp)
      )
    }
  }

  def dollarToNumber(dlr: String): Option[String] = {
    val p = "[0-9.]+".r
    p.findFirstIn(dlr)
  }

  def numberStringToDouble(strDlr: String): Double = {
    val numberStr = dollarToNumber(strDlr)
    numberStr.getOrElse("0").toDouble
  }

  def getCoinCode(strCoin: String): String = {
    strCoin.split(" ")(0)
  }
}
