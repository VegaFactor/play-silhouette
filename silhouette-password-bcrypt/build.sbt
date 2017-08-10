import Dependencies._

version := "4.0.1"

libraryDependencies ++= Seq(
  Library.jbcrypt,
  Library.Specs2.core % Test
)

enablePlugins(Doc)
