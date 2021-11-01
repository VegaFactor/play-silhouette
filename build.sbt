/**
 * Copyright 2015 Mohiva Organisation (license at mohiva dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import sbt.Keys._
import sbt._

val commonSettings = Seq(
  githubOwner       := "VegaFactor",
  githubRepository  := "play-silhouette",
  githubTokenSource := TokenSource.Environment("GITHUB_TOKEN") || TokenSource.GitConfig("github.token"),
)

val silhouette = Project(
  id = "play-silhouette",
  base = file("silhouette")
).settings(commonSettings: _*)

val silhouetteCas = Project(
  id = "play-silhouette-cas",
  base = file("silhouette-cas")
).dependsOn(silhouette % "compile->compile;test->test")
  .settings(commonSettings: _*)

val silhouetteTotp = Project(
  id = "play-silhouette-totp",
  base = file("silhouette-totp")
).dependsOn(silhouette % "compile->compile;test->test")
  .settings(commonSettings: _*)

val silhouetteCryptoJca = Project(
  id = "play-silhouette-crypto-jca",
  base = file("silhouette-crypto-jca")
).dependsOn(silhouette)
  .settings(commonSettings: _*)

val silhouettePasswordBcrypt = Project(
  id = "play-silhouette-password-bcrypt",
  base = file("silhouette-password-bcrypt")
).dependsOn(silhouette)
  .settings(commonSettings: _*)

val silhouettePersistence = Project(
  id = "play-silhouette-persistence",
  base = file("silhouette-persistence")
).dependsOn(silhouette)
  .settings(commonSettings: _*)

val silhouetteTestkit = Project(
  id = "play-silhouette-testkit",
  base = file("silhouette-testkit")
).dependsOn(silhouette)
  .settings(commonSettings: _*)

publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)

val root =
  project.in(file("."))
    .enablePlugins(ScalaUnidocPlugin)
    .settings(
      Defaults.coreDefaultSettings,
//      APIDoc.settings,
      publishLocal := {},
      publishM2 := {},
      publishArtifact := false
    )
    .settings(commonSettings: _*)
    .aggregate(
      silhouette,
      silhouetteCas,
      silhouetteTotp,
      silhouetteCryptoJca,
      silhouettePasswordBcrypt,
      silhouettePersistence,
      silhouetteTestkit
    )
