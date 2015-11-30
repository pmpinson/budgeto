package org.pmp.budgeto

import akka.actor._
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.pmp.budgeto.controller._
import org.pmp.budgeto.domain.CommandSuccess
import org.pmp.budgeto.domain.account.{AccountActor, AccountCreated, CreateAccount}
import org.pmp.budgeto.view.account.AccountViewActor
import play.core.server.{NettyServer, ServerConfig}

import scala.concurrent.Await
import scala.concurrent.duration._

object Bootstrap extends App {
  val server = new Server()

  Runtime.getRuntime.addShutdownHook(new Thread(new Runnable {
    override def run(): Unit = {
      server.stop
    }
  }))

}

import akka.pattern.ask

class Server() {

  //val config = ConfigFactory.parseString("play.crypto.secret=\"1111\"")


  val system = ActorSystem("budgeto")
  implicit val timeout = Timeout(10.seconds)

  val eventLog = system.actorOf(LeveldbEventLog.props(logId = "accounts", prefix = "log"))

  val accountActor = system.actorOf(Props(new AccountActor("1", eventLog)))
  accountActor ? CreateAccount("testAccount", "a note", 125)
  val CommandSuccess(AccountCreated(account)) = Await.result(accountActor ? CreateAccount("testAccount2", "a note5", 125), Duration.Inf)
  accountActor ? CreateAccount(account.id, "")
  val accountViewActor = system.actorOf(Props(new AccountViewActor("2", eventLog)))

  val router = new Router(new IsAliveCtrl, new AccountCtrl(accountViewActor))

  val serverConfig = ServerConfig(port = Some(9000))

  val nettyServer = NettyServer.fromRouter(serverConfig)(router.routes)

  def stop(): Unit = {
    system.terminate()
    nettyServer.stop()
  }

}
