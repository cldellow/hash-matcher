name := "hash-matcher"

organization := "com.cldellow"

version := "0.0.1"

scalaVersion := "2.12.8"

//Define dependencies. These ones are only required for Test and Integration Test scopes.
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "dk.brics" % "automaton" % "1.12-1",
  "de.siegmar" % "fastcsv" % "1.0.3",
  "com.cldellow" %% "warc-framework" % "0.0.1",
  "com.hankcs" % "aho-corasick-double-array-trie" % "1.2.1"
)

// For Settings/Task reference, see http://www.scala-sbt.org/release/sxr/sbt/Keys.scala.html

// Compiler settings. Use scalac -X for other options and their description.
// See Here for more info http://www.scala-lang.org/files/archive/nightly/docs/manual/html/scalac.html 
scalacOptions ++= List("-feature","-deprecation", "-unchecked", "-Xlint")

// ScalaTest settings.
// Ignore tests tagged as @Slow (they should be picked only by integration test)
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "org.scalatest.tags.Slow", "-u","target/junit-xml-reports", "-oD", "-eS")

useGpg := true

// needed so jft doesn't get loaded 2x in same jvm
//fork := true

//coverageEnabled := true

ThisBuild / organization := "com.cldellow"
ThisBuild / organizationName := "com.cldellow"
ThisBuild / organizationHomepage := Some(url("https://github.com/cldellow"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/cldellow/hash-matcher"),
    "scm:git@github.com:cldellow/hash-matcher.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id    = "cldellow",
    name  = "Colin Dellow",
    email = "cldellow@gmail.com",
    url   = url("https://cldellow.com")
  )
)

ThisBuild / description := "Constant-time needle-in-string searches."
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/cldellow/hash-matcher"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
