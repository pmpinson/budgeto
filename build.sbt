import com.stw.sbt.dependencies.PlayDependencies._
import scoverage.ScoverageKeys._

resolvers += "Eventuate Releases" at "https://dl.bintray.com/rbmhtechnology/maven"

lazy val projectDependencies = playNetty() ++ playWs() ++ playJson() ++ Seq(
  "com.typesafe.akka" %% "akka-agent" % "2.3.12",
  "com.rbmhtechnology" %% "eventuate" % "0.2.2"
)

lazy val projectTestDependencies = Seq(
  "org.scalatestplus" %% "play" % "1.4.0-M4" % Test,
  "org.scalatest" %% "scalatest" % "2.2.2" % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "budgeto",
    organization := "org.pmp",
    version := "1.0.0",
    scalaVersion := "2.11.4",
    libraryDependencies ++= projectDependencies ++ projectTestDependencies,
    publishMavenStyle := true,

    coverageMinimum := 95,
    coverageFailOnMinimum := true,

parallelExecution in Test := false
  )