
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._

class $project;format="Camel"$Simulation extends Simulation {

	def apply = {

		val httpConf = httpConfig
			.baseURL("http://localhost:8889")
			.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.acceptLanguageHeader("en-us")
			.acceptEncodingHeader("gzip, deflate")
			.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/536.26.14 (KHTML, like Gecko) Version/6.0.1 Safari/536.26.14")
			.hostHeader("localhost:8889")


		val headers_1 = Map(
			"Connection" -> """keep-alive""",
			"Pragma" -> """no-cache"""
		)


		val scn = scenario("Scenario Name")
			.exec(http("request_1")
					.get("/$project$")
					.headers(headers_1)
			)
			.exec(http("request_2")
					.get("/$project$")
					.headers(headers_1)
			)
			.			pause(3)
			.exec(http("request_3")
					.get("/$project$")
					.headers(headers_1)
			)
			.			pause(2)

		List(scn.configure.users(1000).ramp(10).protocolConfig(httpConf))
	}
}