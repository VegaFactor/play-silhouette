import Dependencies._

version := "4.0.1"

libraryDependencies ++= Seq(
  Library.Play.test,
  Library.Play.specs2 % Test,
  Library.Play.Specs2.matcherExtra % Test,
  Library.Play.Specs2.mock % Test,
  Library.scalaGuice % Test,
  Library.akkaTestkit % Test
)

enablePlugins(PlayScala, Doc)
