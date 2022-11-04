import Dependencies._

version := "7.0.2"

libraryDependencies ++= Seq(
  Library.Specs2.core % Test,
  Library.Specs2.matcherExtra % Test
)

enablePlugins(Doc)
