
import com.stw.sbt.dependencies.PlayDependencies._

resolvers += "Eventuate Releases" at "https://dl.bintray.com/rbmhtechnology/maven"

lazy val projectDependencies = playNetty() ++ playWs() ++ playJson() ++ Seq(
  "com.typesafe.akka" %% "akka-agent" % "2.3.12",
  "org.reactivemongo" %% "reactivemongo" % "0.11.7",
  "com.rbmhtechnology" %% "eventuate" % "0.2.2"
)

lazy val projectTestDependencies = Seq(
  "org.scalatestplus" %% "play" % "1.4.0-M4" % Test,
  "org.mockito" % "mockito-all" % "1.9.5" % Test,
  "org.scalatest" %% "scalatest" % "2.2.2" % Test,
  "com.jayway.restassured" % "rest-assured" % "2.5.0" % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "budgeto",
    version := "1.0.0",
    scalaVersion := "2.11.4",
    libraryDependencies ++= projectDependencies ++ projectTestDependencies
  )