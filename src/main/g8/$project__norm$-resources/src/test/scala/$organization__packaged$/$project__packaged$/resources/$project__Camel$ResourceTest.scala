package $organization$.$project$.resources

import $organization$.$project$.resources.util.Served
import org.specs2.mutable.Specification

object $project;format="Camel"$ResourceTest extends Specification with Served{

  import dispatch._

  def setup = _.plan(new $project;format="Camel"$Resource)

  "The project $project;format="Camel"$Resources" should {
    "serve unfiltered text" in {
      val svc = url("http://localhost:" + port + "/$project$")

      try{
        val response = Http(svc OK as.String)
        response() must beMatching("Hello, $project$")
      } finally{
        Http.shutdown()
      }
  }
}

}