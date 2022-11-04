import Dependencies._

version := "7.0.2"

libraryDependencies ++= Seq(
  Library.Specs2.core % Test,
  Library.Specs2.matcherExtra % Test,
  Library.Specs2.mock % Test,
  Library.scalaGuice % Test,
  Library.Play.test % Test
)

enablePlugins(Doc)
