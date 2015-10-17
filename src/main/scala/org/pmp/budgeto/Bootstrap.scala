package org.pmp.budgeto

import akka.actor._
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.pmp.budgeto.domain.account.AccountActor
import org.pmp.budgeto.view.account.AccountViewActor

import scala.concurrent.duration._

object Bootstrap extends App {

  val system = ActorSystem("budgeto")
  implicit val timeout = Timeout(10.seconds)

  val eventLog = system.actorOf(LeveldbEventLog.props(logId = "accounts", prefix = "log"))

  system.actorOf(Props(new AccountActor("1", eventLog)))
  system.actorOf(Props(new AccountViewActor("2", eventLog)))

  //    on register shutdown system.terminate()

}
