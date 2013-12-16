package io.ubix.jclouds.dashboard

import io.ubix.jclouds.dashboard.http.{RequestHandler}
import akka.actor.{Props, ActorRef, ActorSystem}
import spray.servlet.WebBoot

class Boot extends WebBoot {
  // we need an ActorSystem to host our application in
  val system = ActorSystem("jCloudsActors")
  val serviceActor = system.actorOf(Props[RequestHandler])

  system.registerOnTermination {
    system.log.info("Application shut down")
  }
}
