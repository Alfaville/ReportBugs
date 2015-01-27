name := """reportBugs"""

version := "1.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.postgresql" % "postgresql" % "9.2-1003-jdbc4",
  "org.webjars" % "bootstrap" % "3.3.0",
  "org.webjars" % "bootstrapvalidator" % "0.5.0",
  "org.webjars" % "metisMenu" % "1.1.2",
  "org.webjars" % "font-awesome" % "4.2.0",
  "org.webjars" % "datatables" % "1.10.4",
  "org.webjars" % "jQuery-Autocomplete" % "1.2.7",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)
