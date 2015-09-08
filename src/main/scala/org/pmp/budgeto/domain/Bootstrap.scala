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

  val accountActor = system.actorOf(Props(new AccountActor("1", eventLog)))
  val operationActor = system.actorOf(Props(new AccountOperationActor("2", eventLog)))

  for {
    CreateAccountSuccess(checkingAccount) <- accountActor ? CreateAccount("Checking account", "account for current operation")

  } yield {
    accountActor ! CreateAccount("Book A", "account for saving and taxes")

    for {
      CreateAccountSuccess(sharedAccount) <- accountActor ? CreateAccount("Shared", "Account shared")
    } yield {
        accountActor ! PrintAccounts

        println(s"the deleted SharedAccount $sharedAccount")
        for {
          CloseAccountSuccess(id) <- accountActor ? CloseAccount(sharedAccount.id)
        } yield {
          accountActor ! PrintAccounts

          operationActor ! CreateAccountOperation(checkingAccount.id, "deposit 1", 150d)
          operationActor ! CreateAccountOperation(checkingAccount.id, "deposit 2", 200d)
          operationActor ! CreateAccountOperation(checkingAccount.id, "shopping", -75.5d)

          accountActor ! PrintAccounts
          operationActor ! PrintAccountsOperations
        }
      }
  }

//    system.terminate()

}
