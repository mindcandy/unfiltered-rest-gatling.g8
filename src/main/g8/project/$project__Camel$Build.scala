import _root_.net.virtualvoid.sbt.graph.Plugin._
import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import sbtassembly.Plugin.AssemblyKeys._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease._
import sbtrelease.ReleaseStateTransformations._
import scala.Some
import scala.Some

object $project;format="Camel"$Build extends Build {

  //Resolvers
  lazy val commonResolvers = Seq(
    "Codahale Repo" at "http://repo.codahale.com",
    "Sonatype Repo" at "https://oss.sonatype.org/content/repositories/releases/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Excilys" at "http://repository.excilys.com/content/groups/public",
    "sbt-idea-repo" at "http://mpeltonen.github.com/maven/",
    Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
  )

  //Dependencies
  lazy val commonDependencies = Seq(
    "org.specs2" %% "specs2" % "1.12.2" % "test",
    "com.excilys.ebi.gatling" % "gatling-app" % "1.3.3" % "test",
    "com.excilys.ebi.gatling" % "gatling-recorder" % "1.3.3" % "test",
    "com.excilys.ebi.gatling.highcharts" % "gatling-charts-highcharts" % "1.3.3" % "test"
  )

  //Build Settings applied to all projects
  lazy val commonBuildSettings = Seq(
    organization := "$organization$.$project$",
    scalaVersion := "$scala_version$",
    resolvers ++= commonResolvers
  )

  //Settings applied to all projects
  lazy val defaultSettings = Defaults.defaultSettings ++ assemblySettings ++ commonBuildSettings ++ releaseSettings ++ Seq(
    libraryDependencies ++= commonDependencies,
    releaseProcess := releaseSteps,
    fork in test := true,  //Fork a new JVM for running tests
    javaOptions in (Test,run) += "-XX:MaxPermSize=128m"
  )

  //Release steps for sbt-release plugin
  lazy val releaseSteps = Seq[ReleaseStep](
    //checkSnapshotDependencies,              // Check whether the working directory is a git repository and the repository has no outstanding changes
    //inquireVersions,                        // Ask the user for the release version and the next development version
    runTest                                // Run test:test, if any test fails, the release process is aborted
    //setReleaseVersion,                      // Write version in ThisBuild := "releaseVersion" to the file version.sbt and also apply this setting to the current build state.
    //commitReleaseVersion                   // Commit the changes in version.sbt.
    //tagRelease                             // Tag the previous commit with version (eg. v1.2, v1.2.3).
    //publishArtifacts,                       // Run publish.
    //setNextVersion,                         // Write version in ThisBuild := "nextVersion" to the file version.sbt and also apply this setting to the current build state.
    //commitNextVersion,                      // Commit the changes in version.sbt.
    //pushChanges                             // Push changes
  )

  //Project Dependencies
  lazy val serverDependencies = Seq(
    "net.databinder" %% "unfiltered" % "$unfiltered_version$",
    "net.databinder" %% "unfiltered-filter" % "$unfiltered_version$",
    "net.databinder" %% "unfiltered-netty" % "$unfiltered_version$",
    "net.databinder" %% "unfiltered-netty-server" % "$unfiltered_version$",
    "commons-daemon" % "commons-daemon" % "1.0.10"
  )

  lazy val resourcesDependencies = Seq(
    "net.databinder" %% "unfiltered-netty" % "$unfiltered_version$",
    "net.databinder" %% "unfiltered" % "$unfiltered_version$",
    "net.databinder" %% "unfiltered-netty-server" % "$unfiltered_version$",
    "net.databinder.dispatch" %% "dispatch-core" % "$dispatch_version$"
  )

  //Main project configuration
  lazy val root = Project(
    id = "$name;format="lower"$",
    base = file("."),
    settings = defaultSettings ++ Seq(
      mainClass in(Compile, run) := Some("$organization$.$project$.server.$project;format="Camel"$App"),
      mainClass in assembly := Some("$organization$.$project$.server.$project;format="Camel"$App")) ,
    aggregate = Seq($project$Server, $project$Resources, $project$Core)
  ) .settings(graphSettings: _*)
    .dependsOn($project$Server)

  //Sub project configurations
  lazy val $project$Server = Project(
    id = "$project$-server",
    base = file("$project$-server"),
    settings = defaultSettings ++ Seq(
      retrieveManaged := true,
      libraryDependencies ++= serverDependencies,
      jarName in assembly <<= (crossTarget, version) {
        (target, versionString) => "$project$-" + versionString + ".jar"
      },
      publishArtifact in(Compile, packageBin) := false,
      artifact in(Compile, assembly) ~= {
        (art: Artifact) => art
      }
    )
  ) .configs( IntTest, LoadTest )
    .settings(inConfig(IntTest)(Defaults.testSettings) : _*)
    .settings(inConfig(LoadTest)(Defaults.testSettings) : _*)
    .settings(graphSettings: _*)
    .dependsOn ($project$Resources, $project$Core)

  lazy val $project$Resources = Project(
    id = "$project$-resources",
    base = file("$project$-resources"),
    settings = defaultSettings ++ Seq(
      libraryDependencies ++= resourcesDependencies,
      artifact in(Compile, assembly) ~= {
        (art: Artifact) => art
      }
    )
  ) .settings(graphSettings: _*)

  lazy val $project$Core = Project(
    id = "$project$-core",
    base = file("$project$-core"),
    settings = defaultSettings ++ Seq(
      libraryDependencies ++= resourcesDependencies,
      artifact in(Compile, assembly) ~= {
        (art: Artifact) => art
      }
    )
  ) .settings(graphSettings: _*)

  //Additional Test configurations (integration test 'it' is a standard configuration, but we want it as 'test-integration')
  lazy val LoadTest = config("test-load") extend Test
  lazy val IntTest = config("test-integration") extend Test
}
