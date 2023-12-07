
  val appName = "ct-calculations"

  lazy val scoverageSettings = {
    import scoverage._
    Seq(
      ScoverageKeys.coverageMinimum := 80,
      ScoverageKeys.coverageFailOnMinimum := false,
      ScoverageKeys.coverageHighlighting := true,
      ScoverageKeys.coverageExcludedFiles := ";.*Routes.*;views.*",
      ScoverageKeys.coverageExcludedPackages := """<empty>;.*javascript.*;.*models.*;.*Routes.*;.*testonly.*""",
      Test / parallelExecution := false
    )
  }

  lazy val CtCalculations = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin)
    .disablePlugins(JUnitXmlReportPlugin)
    .settings(majorVersion := 2)
    .settings(scoverageSettings: _*)
    .settings(

      //TODO: Remove before master merge
      headerLicense := Some(HeaderLicense.Custom(
        """Copyright 2023 HM Revenue & Customs
          |
          |Licensed under the Apache License, Version 2.0 (the "License");
          |you may not use this file except in compliance with the License.
          |You may obtain a copy of the License at
          |
          |    http://www.apache.org/licenses/LICENSE-2.0
          |
          |Unless required by applicable law or agreed to in writing, software
          |distributed under the License is distributed on an "AS IS" BASIS,
          |WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
          |See the License for the specific language governing permissions and
          |limitations under the License.
          |""".stripMargin
      )))
    .settings(
      name := appName,
      scalaVersion := "2.13.10",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play-json-joda" % "2.9.4",
        "org.scalatest" %% "scalatest" % "3.2.16" % "test",
        "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % "test",
        "com.vladsch.flexmark" % "flexmark-all" % "0.64.8" % "test",
        "org.pegdown" % "pegdown" % "1.6.0" % "test"
      )
    )
