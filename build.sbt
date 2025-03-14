lazy val V = new {
  val Scala3     = "3.3.5"
  val Zio        = "2.1.16"
  val ZioConfig  = "4.0.3"
  val ZioLogging = "2.5.0"
  val Logback    = "1.5.17"
}

lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name                              := "zio-config-issue",
    scalaVersion                      := V.Scala3,
    organization                      := "com.appleseed",
    libraryDependencies ++= Seq(
      "dev.zio"       %% "zio"                 % V.Zio,
      "dev.zio"       %% "zio-config"          % V.ZioConfig,
      "dev.zio"       %% "zio-config-typesafe" % V.ZioConfig,
      "dev.zio"       %% "zio-config-magnolia" % V.ZioConfig,
      "dev.zio"       %% "zio-logging"         % V.ZioLogging,
      "dev.zio"       %% "zio-logging-slf4j"   % V.ZioLogging,
      "ch.qos.logback" % "logback-classic"     % V.Logback
    ),
    ThisBuild / assemblyMergeStrategy := {
      case PathList("META-INF", _) => MergeStrategy.discard
      case _                       => MergeStrategy.first
    },
    Compile / mainClass               := Some("com.appleseed.MainApp"),
    Compile / doc / sources           := Seq.empty,
    NativePackagerKeys.bashScriptExtraDefines ++= Seq(
      s""" addJava "-Dlogback.configurationFile=conf/logback.xml" """
    ),
    Universal / mappings ++= Seq(
      (Compile / resourceDirectory).value / "application.dev.conf"  -> "conf/application.dev.conf",
      (Compile / resourceDirectory).value / "application.test.conf" -> "conf/application.test.conf",
      (Compile / resourceDirectory).value / "logback.xml"           -> "conf/logback.xml"
    )
  )
