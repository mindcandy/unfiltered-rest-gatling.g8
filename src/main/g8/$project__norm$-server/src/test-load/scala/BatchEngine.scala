import com.excilys.ebi.gatling.app.SimulationClassLoader
import com.excilys.ebi.gatling.core.util.PathHelper.path2string

import java.lang.System.currentTimeMillis
import java.util.{Map => JMap}

import com.excilys.ebi.gatling.charts.report.ReportsGenerator
import com.excilys.ebi.gatling.core.config.{GatlingFiles, GatlingPropertiesBuilder}
import com.excilys.ebi.gatling.core.config.GatlingConfiguration
import com.excilys.ebi.gatling.core.config.GatlingConfiguration.configuration
import com.excilys.ebi.gatling.core.runner.Selection
import com.excilys.ebi.gatling.core.scenario.configuration.Simulation
import com.excilys.ebi.gatling.core.util.FileHelper.formatToFilename

object BatchEngine extends App {
  val props = new GatlingPropertiesBuilder
  props.dataDirectory(IDEPathHelper.dataDirectory)
  props.resultsDirectory(IDEPathHelper.resultsDirectory)
  props.requestBodiesDirectory(IDEPathHelper.requestBodiesDirectory)
  props.binariesDirectory(IDEPathHelper.compiledScenarioDirectory)
  println("Looking for compiled scenarios in: " + IDEPathHelper.compiledScenarioDirectory)
  props.sourcesDirectory(IDEPathHelper.scenariosDirectory)
  props.dataDirectory(IDEPathHelper.scenariosDirectory)

  GatlingConfiguration.setUp(props.build)
  start

  private def start = {

   val simulations = GatlingFiles.binariesDirectory
      .map(// expect simulations to have been pre-compiled (ex: IDE)
      SimulationClassLoader.fromClasspathBinariesDirectory(_))
      .getOrElse(SimulationClassLoader.fromSourcesDirectory(GatlingFiles.sourcesDirectory))
      .simulationClasses(configuration.simulation.clazz)
      .sortWith(_.getName < _.getName)

    val selections = simulations.map {
      s => new Selection(s, defaultOutputDirectoryBaseName(s), defaultOutputDirectoryBaseName(s))
    }

    val outDirs = new MultiRunner(selections).runSelections
    if (!configuration.charting.noReports) outDirs.foreach(s => generateReports(s))
  }

  private def defaultOutputDirectoryBaseName(clazz: Class[Simulation]) = configuration.simulation.outputDirectoryBaseName.getOrElse(formatToFilename(clazz.getSimpleName))

  private def generateReports(outputDirectoryName: String) {
    println("Generating reports...")
    val start = currentTimeMillis
    val indexFile = ReportsGenerator.generateFor(outputDirectoryName)
    println("Reports generated in " + (currentTimeMillis - start) / 1000 + "s.")
    println("Please open the following file : " + indexFile)
  }
}