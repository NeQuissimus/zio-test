import sbtcrossproject.CrossPlugin.autoImport.crossProject

inThisBuild(
  List(
    organization := "org.scalaz",
    homepage := Some(url("https://scalaz.github.io/scalaz-zio/")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "NeQuissimus",
        "Tim Steinbach",
        "tim@nequissimus.com",
        url("http://nequissimus.com")
      )
    )
  )
)

addCommandAlias("format", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("validate", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

import Scalaz._

lazy val root = project
  .in(file("."))
  .settings(
    skip in publish := true,
    console := (console in Compile in coreJVM).value
  )
  .aggregate(coreJVM, coreJS)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .in(file("core"))
  .settings(stdSettings("core"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-sbt" % "test-interface" % "1.0",
      "org.scalaz"    %% "scalaz-zio"    % "0.16"
    )
  )

lazy val coreJVM = core.jvm.settings(replSettings)
lazy val coreJS  = core.js

lazy val test = crossProject(JSPlatform, JVMPlatform)
  .in(file("test"))
  .settings(stdSettings("core"))
  .settings(testFrameworks += new TestFramework("scalaz.zio.test.TestFramework"))
  .dependsOn(core)

lazy val testJVM = test.jvm
lazy val testJS = test.js
