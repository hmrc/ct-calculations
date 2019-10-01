import sbt.{Def, _}
import sbt.Keys._
import uk.gov.hmrc.{SbtArtifactory, SbtAutoBuildPlugin}
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import uk.gov.hmrc.SbtArtifactory.autoImport.makePublicallyAvailableOnBintray


object HmrcBuild extends Build {

  import BuildDependencies._
  import uk.gov.hmrc.DefaultBuildSettings._

  val appName = "ct-calculations"

  lazy val CtCalculations = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
    .settings(majorVersion := 2)
    .settings(makePublicallyAvailableOnBintray := true)
    .settings(
      name := appName,
      scalaVersion := "2.11.12",
      crossScalaVersions := Seq("2.11.12"),
      libraryDependencies ++= Seq(
        Compile.playJson,
        Compile.catoTime,
        Compile.jodaJson,
        Test.scalaTest,
        Test.pegdown,
        Test.mockito,
        Test.hamcrest
      ),
      Developers()
    )
}

private object BuildDependencies {

  object Compile {
    val playJson = "com.typesafe.play" %% "play-json" % "2.6.13" % "provided"
    val jodaJson = "com.typesafe.play" % "play-json-joda_2.11" % "2.6.0"
    val catoTime = "uk.gov.hmrc" %% "play-time" % "0.4.0" % "provided"
  }

  sealed abstract class Test(scope: String) {
    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.6" % scope
    val mockito = "org.mockito" % "mockito-all" % "1.10.19" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.6.0" % scope
    val hamcrest = "org.hamcrest" % "hamcrest-all" % "1.3" % scope
  }

  object Test extends Test("test")

}

object Developers {

  def apply(): Def.Setting[List[Developer]] = developers := List[Developer]()
}
