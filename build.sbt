
lazy val root = project.in(file("."))
  .aggregate(time4s)
  .aggregate(`time4s-test`)


lazy val commonSettings = Seq(
  organization := "com.github.yaroot.time4s",
  scalaVersion := "2.13.2",
  crossScalaVersions := List("2.12.11", "2.13.2"),
  scalacOptions in (Compile, console) --= Seq("-Ywarn-unused:imports", "-Xfatal-warnings"),
  publish / skip := true,
  scalafmtOnCompile := true,
  cancelable in Global := true,
  fork in run := true,
  Test / fork := true,
  parallelExecution in Test := false,
  addCompilerPlugin("org.typelevel"    % "kind-projector"      % "0.11.2" cross CrossVersion.full),
  // wartremoverErrors in (Compile, compile) ++= Warts.unsafe, // .all
  wartremoverErrors := Nil,
  testFrameworks += new TestFramework("minitest.runner.Framework"),
  version ~= (_.replace('+', '-')),
  dynver ~= (_.replace('+', '-')),
)


lazy val testDeps = Seq(
  libraryDependencies ++= {
    Seq(
      "io.monix"          %% "minitest"                     % "2.9.1"
    )
  }
)


lazy val time4s = project.in(file("time4s"))
  .settings(commonSettings)
  .settings(publish / skip := false)
  .settings(
    libraryDependencies ++= {
      Seq(
      )
    }
  )


lazy val `time4s-test` = project.in(file("time4s-test"))
  .settings(commonSettings)
  .settings(testDeps)
  .dependsOn(time4s)

