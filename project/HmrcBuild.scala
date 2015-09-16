import sbt._
import Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.PublishingSettings._
  import uk.gov.hmrc.NexusPublishing._
  import uk.gov.hmrc.DefaultBuildSettings
  import scala.util.Properties.envOrElse
  import DefaultBuildSettings._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}
  import Dependencies._

  val nameApp = "cato-calculators"
  val versionApp = envOrElse("CATO_CALCULATORS_VERSION", "999-SNAPSHOT")

  val appDependencies = Seq(
    Compile.playJson,

    Test.scalaTest,
    Test.pegdown,
    Test.scalaCheck
  )

  lazy val calculators = (project in file("."))
    .settings(name := nameApp)
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings() : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      crossScalaVersions := Seq("2.11.2", "2.10.4")
    )
    .settings(publishAllArtefacts: _*)
    .settings(nexusPublishingSettings: _*)
    .settings(SbtBuildInfo(): _*)
}

object Dependencies {

  object Compile {
    val playJson = "com.typesafe.play" %% "play-json" % "2.3.2" % "provided"
  }

  sealed abstract class Test(scope: String) {

    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.0" % scope
    val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.11.5" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.4.2" % scope
  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}
