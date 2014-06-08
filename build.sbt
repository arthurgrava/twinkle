organization := "org.goodfellas"

name := "twinkle"

version := "1.0"

autoScalaLibrary := false

publishMavenStyle := true

crossPaths := false

mainClass in (Compile, run) := Some("org.goodfellas.App")

mainClass in (Compile, packageBin) := Some("org.goodfellas.App")

libraryDependencies ++= Seq( 
    "junit" % "junit" % "4.10",
    "com.novocode" % "junit-interface" % "0.10-M1" % "test"
)