package $organization$.$project$.resources

import unfiltered.request._
import unfiltered.response._
import unfiltered.netty.cycle.Plan
import unfiltered.netty.cycle.ThreadPool
import org.jboss.netty.channel.ChannelHandlerContext

class $project;format="Camel"$Resource extends Plan with ThreadPool {

  def intent = {
    case req @ Path(Seg("$project$" :: Nil)) => req match {
      case GET(_) => ResponseString("Hello, $project$")
      case _      => MethodNotAllowed ~> ResponseString("Only GET supported\n")
    }
    case _ => ResponseString("Request not valid\n")
  }

  def onException(ctx: ChannelHandlerContext, t: Throwable) {
    //Be sure to add proper error handling
    println("Something went wrong")
  }

}