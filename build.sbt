
  val appName = "ct-calculations"

  lazy val scoverageSettings = {
    import scoverage._
    Seq(
      ScoverageKeys.coverageMinimumStmtTotal := 80,
      ScoverageKeys.coverageFailOnMinimum := false,
      ScoverageKeys.coverageHighlighting := true,
      ScoverageKeys.coverageExcludedFiles := ";.*Routes.*;views.*",
      ScoverageKeys.coverageExcludedPackages := """<empty>;.*javascript.*;.*models.*;.*Routes.*;.*testonly.*""",
      Test / parallelExecution := false
    )
  }
  val scope = "test"
  lazy val CtCalculations = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin)
    .disablePlugins(JUnitXmlReportPlugin)
    .settings(majorVersion := 2)
    .settings(scoverageSettings: _*)
    .settings(
      name := appName,
      scalaVersion := "2.13.10",
      libraryDependencies ++= Seq(
        "org.playframework" %% "play-json-joda" % "3.0.1",
        "org.scalatest" %% "scalatest" % "3.2.18" % scope,
        "org.apache.pekko" %% "pekko-testkit" % "1.0.2" % scope,
        "org.scalatestplus" %% "mockito-4-11" % "3.2.17.0" % scope,
        "com.vladsch.flexmark" % "flexmark-all" % "0.64.8" % scope,
        "org.pegdown" % "pegdown" % "1.6.0" % "test"
      )
    )
