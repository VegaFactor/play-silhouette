import Dependencies._

version := "7.0.1"

libraryDependencies ++= Seq(
  Library.Specs2.core % Test,
  Library.Specs2.matcherExtra % Test
)

enablePlugins(Doc)
