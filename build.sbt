import sbt.{Def, _}
import sbt.Keys._
import uk.gov.hmrc.{SbtArtifactory, SbtAutoBuildPlugin}
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import uk.gov.hmrc.SbtArtifactory.autoImport.makePublicallyAvailableOnBintray

  val appName = "ct-calculations"

  lazy val CtCalculations = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
    .disablePlugins(JUnitXmlReportPlugin)
    .settings(majorVersion := 2)
    .settings(makePublicallyAvailableOnBintray := true)
    .settings(
      name := appName,
      scalaVersion := "2.11.12",
      crossScalaVersions := Seq("2.11.12"),
      libraryDependencies ++= Seq(
        "com.typesafe.play" % "play-json-joda_2.11" % "2.7.4",
        "uk.gov.hmrc" %% "play-time" % "0.9.0" % "provided",
        "org.scalatest" %% "scalatest" % "3.0.8" % "test",
        "org.mockito" % "mockito-all" % "1.10.19" % "test",
        "org.pegdown" % "pegdown" % "1.6.0" % "test"
      ),
      developers := List[Developer]()
    )
