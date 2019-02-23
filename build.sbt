name := """play-cookapi"""
organization := "com.example"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

enablePlugins(PlayEbean)

//libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"
libraryDependencies += guice
libraryDependencies += evolutions
libraryDependencies += jdbc

assemblyJarName in assembly := "thecookapi42.jar"
test in assembly := {}

