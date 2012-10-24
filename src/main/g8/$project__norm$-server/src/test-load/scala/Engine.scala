import com.excilys.ebi.gatling.app.Gatling
import com.excilys.ebi.gatling.core.config.GatlingPropertiesBuilder
import com.excilys.ebi.gatling.core.util.PathHelper.path2string

object Engine extends App {

  val props = new GatlingPropertiesBuilder
  props.dataDirectory(IDEPathHelper.dataDirectory)
  props.resultsDirectory(IDEPathHelper.resultsDirectory)
  props.requestBodiesDirectory(IDEPathHelper.requestBodiesDirectory)
  props.binariesDirectory(IDEPathHelper.compiledScenarioDirectory)
  println("Looking for compiled scenarios in: " + IDEPathHelper.compiledScenarioDirectory)
  props.sourcesDirectory(IDEPathHelper.scenariosDirectory)
  props.dataDirectory(IDEPathHelper.scenariosDirectory)

  Gatling.fromMap(props.build)
}