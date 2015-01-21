val junit = "junit" % "junit" % "4.12" % "test"

val junitLauncher = "com.novocode" % "junit-interface" % "0.11" % "test"

lazy val root = (project in file(".")).
  settings(
    name := "budgeto-service",
    version := "1.0-SNAPSHOT",
    libraryDependencies += junit,
    libraryDependencies += junitLauncher
  )