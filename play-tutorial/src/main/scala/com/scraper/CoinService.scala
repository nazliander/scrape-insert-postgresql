package com.scraper

import java.sql.Connection

import javax.inject.{Inject, Singleton}

@Singleton
class CoinService @Inject()(coinRepository: CoinRepository) {

  def add(input: Coin.Create)(implicit conn: Connection): Int = {
    coinRepository.create(input)
  }
}
