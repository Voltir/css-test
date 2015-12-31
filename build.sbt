import sbt.Project.projectToRef

lazy val clients = Seq(clientScalaCSS)
lazy val scalaV = "2.11.7"

lazy val server = (project in file("server")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd, gzip),
  libraryDependencies ++= Seq(
    "com.vmunier" %% "play-scalajs-scripts" % "0.3.0"
  )
).enablePlugins(PlayScala)
 .aggregate(clients.map(projectToRef): _*)
 .dependsOn(sharedScalaCSSJvm)

lazy val clientScalaCSS = (project in file("client-scalacss")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0",
    "com.lihaoyi" %%% "scalatags" % "0.5.3",
    "com.github.japgolly.scalacss" %%% "core" % "0.3.1",
    "com.github.japgolly.scalacss" %%% "ext-scalatags" % "0.3.1"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(sharedScalaCSSJs)

lazy val sharedScalaCSS = (crossProject.crossType(CrossType.Pure) in file("shared-scalacss")).
  settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.5.3",
      "com.github.japgolly.scalacss" %%% "core" % "0.3.1",
      "com.github.japgolly.scalacss" %%% "ext-scalatags" % "0.3.1"
    )
  ).jsConfigure(_ enablePlugins ScalaJSPlay)

lazy val sharedScalaCSSJvm = sharedScalaCSS.jvm
lazy val sharedScalaCSSJs = sharedScalaCSS.js

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
