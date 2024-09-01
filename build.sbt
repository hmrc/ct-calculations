
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

  lazy val CtCalculations = (project in file("."))
    .disablePlugins(JUnitXmlReportPlugin)
    .settings(majorVersion := 2)
    .settings(scoverageSettings: _*)
    .settings(
      name := appName,
      scalaVersion := "2.13.12",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play-json" % "2.10.4",
        "org.scalatest" %% "scalatest" % "3.2.18" % "test",
        "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % "test",
        "com.vladsch.flexmark" % "flexmark-all" % "0.64.8" % "test"
      ),
      scalacOptions ++= Seq("-Wconf:msg=eta-expanded:ws")
    )
