val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
val scalaXml =  "org.scala-lang.modules" %% "scala-xml" % "1.0.2"

lazy val commonSettings = Seq(
  //organization := "com.example",
  //version := "0.1.0",
  scalaVersion := "2.11.6",
  
  mainClass in (Compile, run) := Some("de.t7n.Ambiance.Main")
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "Ambiance",
    libraryDependencies += scalaTest,
    libraryDependencies += scalaXml
  )

