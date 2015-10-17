package com.stw.sbt.dependencies

import sbt._

object PlayDependencies {

  val playDefaultVersion = "2.4.2"
  val scalaDefaultVersion = "2.11"

  def playJson(playVersion: String = playDefaultVersion) = Seq(
    "com.typesafe.play" %% "play-json" % playVersion excludeAll(
      ExclusionRule("com.fasterxml.jackson.datatype", "jackson-datatype-jdk8"),
      ExclusionRule("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310")
      )
  )

  def playWs(playVersion: String = playDefaultVersion, scalaVersion: String = scalaDefaultVersion) = play(playVersion, scalaVersion) ++ Seq(
    "com.typesafe.play" %% "play-ws" % playVersion excludeAll(
      ExclusionRule("com.typesafe.play", s"play_${scalaVersion}"),
      ExclusionRule("com.google.guava", "guava"),
      ExclusionRule("oauth.signpost", "signpost-core"),
      ExclusionRule("oauth.signpost", "signpost-commonshttp4")
      ),
    "com.ning" % "async-http-client" % "1.9.24"
  )

  def playNetty(playVersion: String = playDefaultVersion, scalaVersion: String = scalaDefaultVersion) = playServer(playVersion, scalaVersion) ++ Seq(
    "com.typesafe.play" %% "play-netty-server" % playVersion excludeAll (
      ExclusionRule("com.typesafe.play", s"play-server_${scalaVersion}")
      )
  )

  def playServer(playVersion: String = playDefaultVersion, scalaVersion: String = scalaDefaultVersion) = play(playVersion, scalaVersion) ++ Seq(
    "com.typesafe.play" %% "play-server" % playVersion excludeAll (
      ExclusionRule("com.typesafe.play", s"play_${scalaVersion}")
      )
  )

  def play(playVersion: String = playDefaultVersion, scalaVersion: String = scalaDefaultVersion) = Seq(
    "com.typesafe.play" %% "play" % playVersion excludeAll(
      ExclusionRule("com.typesafe.play", s"play-json_${scalaVersion}"),
      ExclusionRule("com.typesafe.akka", s"akka-slf4j_${scalaVersion}"),
      ExclusionRule("com.fasterxml.jackson.core", "*"),
      ExclusionRule("com.fasterxml.jackson.datatype", "*"),
      ExclusionRule("commons-codec", "commons-codec"),
      ExclusionRule("org.apache.commons", "commons-lang3"),
      ExclusionRule("javax.transaction", "jta"),
      ExclusionRule("com.google.inject", "guice"),
      ExclusionRule("com.google.inject.extensions", "guice-assistedinject")
      ),

    "javax.inject" % "javax.inject" % "1"
  )

}