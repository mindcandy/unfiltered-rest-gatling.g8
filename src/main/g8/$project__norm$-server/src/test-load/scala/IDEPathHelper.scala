import scala.tools.nsc.io.Path.string2path

import tools.nsc.io.{Path, File}

object IDEPathHelper {
  val projectRootDir = sys.props.getOrElse("user.dir", ".")
  val classDirectory = getClass.getResource("IDEPathHelper.class").getPath
  println(classDirectory)
  val compiledScenarioDirectory = File(Path(classDirectory.toString)).parent
  println(compiledScenarioDirectory)

  val testDir = projectRootDir / "$project$-server" / "src" / "test-load" / "scala"
  val gatlingDir = projectRootDir / "gatling"
  val requestBodiesDirectory = gatlingDir / "requestbodies"
  val scenariosDirectory = testDir / "scenarios/"
  val resultsDirectory = gatlingDir / "results"
  val dataDirectory = gatlingDir / "data"

}