import Dependencies._

version := "7.0.2"

libraryDependencies ++= Seq(
  Library.jbcrypt,
  Library.Specs2.core % Test
)

enablePlugins(Doc)
