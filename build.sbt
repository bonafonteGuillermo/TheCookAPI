name := """play-cookapi"""
organization := "com.example"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

enablePlugins(PlayEbean)

libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"
libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc

assemblyJarName in assembly := "thecookapi.jar"
test in assembly := {}

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    MergeStrategy.concat
  case defaultCreateTable if defaultCreateTable.contains("default-create-table.sql") =>
    MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

