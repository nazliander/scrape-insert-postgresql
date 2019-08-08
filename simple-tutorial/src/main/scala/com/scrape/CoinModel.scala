package com.scrape

import java.sql.Timestamp

case class Coin(id: Int, code: String, price: Double, logTimestamp: Long)

case class CoinCreate(code: String, price: Double)

object CoinCreate {
  def apply(listOfElements: List[String]): CoinCreate = {
    CoinCreate(
      code = getCoinCode(listOfElements(1)),
      price = numberStringToDouble(listOfElements(3))
    )
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

case class CoinInsert(code: String, price: Double, logTimestamp: Timestamp)

object CoinInsert {
  def apply(coin: CoinCreate, logTimestamp: Long): CoinInsert = {
    CoinInsert(
      code = coin.code,
      price = coin.price,
      logTimestamp = new Timestamp(logTimestamp)
    )
  }
}
