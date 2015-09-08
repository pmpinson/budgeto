package org.pmp.budgeto.domain

import java.util.UUID

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor
import org.joda.time.DateTime

import scala.util.{Failure, Success}

/**
 * Objects
 */
case class Account(id: String, label: String, note: String, initialBalance: Double, creationDate: DateTime)

/**
 * list of commands
 */
case class PrintAccounts()

case class CreateAccount(label: String, note: String, initialBalance: Double = 0)

case class CloseAccount(id: String)

/**
 * replies on command
 */
case class CreateAccountSuccess(account: Account)

case class CreateAccountFailure(cause: Throwable)

case class CloseAccountSuccess(id: String)

case class CloseAccountFailure(cause: Throwable)

/**
 * Events
 */
case class AccountCreated(account: Account)

case class AccountClosed(id: String)


class AccountActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedActor {

  private val accounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty
  private val closedAccounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty

  override val onCommand: Receive = {
    case PrintAccounts => {
      println("accounts")
      accounts.foreach { case (k, a) => println(s"\t$a") }
      println("closedAccounts")
      closedAccounts.foreach { case (k, a) => println(s"\t$a") }
    }

    case CreateAccount(label, note, initialBalance) => for {
      account <- Account(UUID.randomUUID().toString, label, note, initialBalance, DateTime.now())
    } persist(AccountCreated(account)) {
        case Success(e) =>
          onEvent(e)
          sender() ! CreateAccountSuccess(account)
        case Failure(err) =>
          sender() ! CreateAccountFailure(err)
      }

    case CloseAccount(id) => for {
      evt <- AccountClosed(id)
    } persist(evt) {
        case Success(e) =>
          onEvent(e)
          sender() ! CloseAccountSuccess(id)
        case Failure(err) =>
          sender() ! CloseAccountFailure(err)
      }
  }

}

override val onEvent: Receive = {
case AccountCreated (account) => accounts.put (account.id, account)
case AccountClosed (id) => closedAccounts.put (id, accounts.remove (id).get)
}
}