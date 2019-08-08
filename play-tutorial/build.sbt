enablePlugins(JavaAppPackaging)

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  "org.postgresql" % "postgresql" % "42.2.5",
  "com.lucidchart" %% "relate" % "2.1.1",
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0"
)

trapExit := false
