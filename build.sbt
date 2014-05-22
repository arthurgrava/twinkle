organization := "org.goodfellas"

name := "twinkle"

version := "1.0-SNAPSHOT"

autoScalaLibrary := false

crossPaths := false

mainClass in (Compile, run) := Some("org.goodfellas.App")

libraryDependencies ++= Seq( 
    "junit" % "junit" % "4.10",
    "com.novocode" % "junit-interface" % "0.10-M1" % "test"
)
