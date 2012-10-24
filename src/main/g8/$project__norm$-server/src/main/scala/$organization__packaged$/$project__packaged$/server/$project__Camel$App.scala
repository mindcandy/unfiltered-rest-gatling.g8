package $organization$.$project$.server

import unfiltered.netty.Http
import $organization$.$project$.resources.$project;format="Camel"$Resource
import org.apache.commons.daemon.{DaemonContext, Daemon}

trait $project;format="Camel"$Config {
  val port = 8889
}

object $project;format="Camel"$App extends App {
  $project;format="Camel"$App(new $project;format="Camel"$Config {}).start
}

case class $project;format="Camel"$App(config: $project;format="Camel"$Config) {
  val httpServer = Http(config.port).plan(new $project;format="Camel"$Resource)

  def start = httpServer run
}

class $project;format="Camel"$Starter extends Daemon{

  def init(context: DaemonContext) = println("Initialising...")

  def start() = $project;format="Camel"$App(new $project;format="Camel"$Config {}).start

  def stop() = println("Stopping...")

  def destroy() = println("Destroying...")

}
