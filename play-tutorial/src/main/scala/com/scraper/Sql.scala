package com.scraper

import com.lucidchart.relate.{InterpolatedQuery, Parameter}

trait Sql {

  val tableName: String

  protected val emptySql = sql""
  protected val commaSql = sql","

  implicit class SqlString(string: String) {
    def unsafeSql = InterpolatedQuery.fromParts(Seq(string), Seq())

    def toSql = unsafeSql
  }

  implicit class SqlStringContext(stringContext: StringContext) {
    def sql(args: Parameter*) =
      InterpolatedQuery.fromParts(stringContext.parts, args)
  }

}
