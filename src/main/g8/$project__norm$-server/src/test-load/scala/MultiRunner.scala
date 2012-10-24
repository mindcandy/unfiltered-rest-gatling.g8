/** *
  * A runner for Gatling tests that executes multiple scenarios at once. Based off
  * of com.excilys.ebi.gatling.core.runner.Runner from the Gatling library
  */
import com.excilys.ebi.gatling.core.runner.Selection
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS

import org.joda.time.DateTime.now

import com.excilys.ebi.gatling.core.action.system
import com.excilys.ebi.gatling.core.config.GatlingConfiguration.configuration
import com.excilys.ebi.gatling.core.result.message.RunRecord
import com.excilys.ebi.gatling.core.result.terminator.Terminator
import com.excilys.ebi.gatling.core.result.writer.DataWriter

import grizzled.slf4j.Logging

class MultiRunner(selection: List[Selection]) extends Logging {

  def runSelections = {
    try {selection.map(s => run(s))}
    finally {
      system.shutdown
    }
  }

  private def run(selection: Selection): String = {
    val simulationClass = selection.simulationClass
    println("Simulation " + simulationClass.getName + " started...")

    val runRecord = RunRecord(now, selection.simulationId, selection.description)

    val scenarios = simulationClass.newInstance.apply().map(_.build)

    require(!scenarios.isEmpty, simulationClass.getName + " returned an empty scenario list")

    val totalNumberOfUsers = scenarios.map(_.configuration.users).sum
    info("Total number of users : " + totalNumberOfUsers)

    val terminatorLatch = new CountDownLatch(1)
    Terminator.init(terminatorLatch, totalNumberOfUsers)
    DataWriter.init(runRecord, scenarios)

    debug("Launching All Scenarios")
    scenarios.foreach(_.run)
    debug("Finished Launching scenarios executions")

    terminatorLatch.await(configuration.timeOut.simulation, SECONDS)
    println("Simulation finished.")

    runRecord.runId
  }

}