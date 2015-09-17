package org.pmp.budgeto.domain

import java.nio.file.Paths

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Bootstrap extends App {

  val system = ActorSystem("budgeto")
  implicit val timeout = Timeout(10.seconds)

  val eventLog = system.actorOf(LeveldbEventLog.props(logId = "accounts", prefix = "log"))

  system.actorOf(Props(new AccountActor("1", eventLog)))
  system.actorOf(Props(new AccountViewActor("2", eventLog)))

//    on register shutdown system.terminate()

}
