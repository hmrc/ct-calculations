
  val appName = "ct-calculations"

  lazy val scoverageSettings = {
    import scoverage._
    Seq(
      ScoverageKeys.coverageMinimum := 80,
      ScoverageKeys.coverageFailOnMinimum := false,
      ScoverageKeys.coverageHighlighting := true,
      ScoverageKeys.coverageExcludedFiles := ";.*Routes.*;views.*",
      ScoverageKeys.coverageExcludedPackages := """<empty>;.*javascript.*;.*models.*;.*Routes.*;.*testonly.*""",
      parallelExecution in Test := false
    )
  }

  lazy val CtCalculations = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
    .disablePlugins(JUnitXmlReportPlugin)
    .settings(majorVersion := 2)
    .settings(scoverageSettings: _*)
    .settings(
      name := appName,
      scalaVersion := "2.12.11",
      libraryDependencies ++= Seq(
        "com.typesafe.play" % "play-json-joda_2.12" % "2.7.4",
        "uk.gov.hmrc" %% "play-time" % "0.14.0",
        "org.scalatest" %% "scalatest" % "3.0.8" % "test",
        "org.mockito" % "mockito-all" % "1.10.19" % "test",
        "org.pegdown" % "pegdown" % "1.6.0" % "test"
      )
    )
