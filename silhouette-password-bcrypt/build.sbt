import Dependencies._

version := "7.0.1"

libraryDependencies ++= Seq(
  Library.jbcrypt,
  Library.Specs2.core % Test
)

enablePlugins(Doc)
