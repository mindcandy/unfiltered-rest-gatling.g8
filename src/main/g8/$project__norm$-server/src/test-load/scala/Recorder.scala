import com.excilys.ebi.gatling.core.util.PathHelper.path2string
import com.excilys.ebi.gatling.recorder.config.RecorderOptions
import com.excilys.ebi.gatling.recorder.controller.RecorderController

object Recorder extends App {

  println("Will save scenarios in: " + IDEPathHelper.scenariosDirectory)

	RecorderController(new RecorderOptions(
		outputFolder = Some(IDEPathHelper.scenariosDirectory),
		requestBodiesFolder = Some(IDEPathHelper.requestBodiesDirectory)))
}