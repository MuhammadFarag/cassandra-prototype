name := "cassandra-prototype"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.outworkers"  %% "phantom-dsl" % "2.20.0",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)