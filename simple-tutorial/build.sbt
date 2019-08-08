name := "simple-tutorial"

version := "0.1"

scalaVersion := "2.11.12"

scalacOptions += "-Ypartial-unification" // 2.11.9+

lazy val doobieVersion = "0.7.0"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0"
)
