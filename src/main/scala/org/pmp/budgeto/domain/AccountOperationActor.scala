package org.pmp.budgeto.domain

import java.util.UUID

import akka.actor.{Props, ActorRef}
import com.rbmhtechnology.eventuate.EventsourcedActor
import org.joda.time.DateTime

import scala.util.{Failure, Success}

/**
 * Objects
 */
case class AccountOperation(id: String, label: String, amount: Double, date: DateTime)

case class AccountOperations(id: String, balance: Double, operations: Map[String, AccountOperation] = Map.empty)

/**
 * list of commands
 */
case class PrintAccountsOperations()

case class CreateAccountOperation(accountId: String, label: String, n: Double)

/**
 * replies on command
 */
case class CreateAccountOperationSuccess(balance: Double)

case class CreateAccountOperationFailure(cause: Throwable)

/**
 * Events
 */
case class AccountOperationCreated(accountId: String, operation: AccountOperation)

class AccountOperationActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedActor {

  val system.actorOf(Props(new AccountActor("1", eventLog)))

  private val accounts: scala.collection.mutable.Map[String, AccountOperations] = scala.collection.mutable.Map.empty

  override val onCommand: Receive = {
    case PrintAccountsOperations => {
      println("accounts operations")
      accounts.foreach { case (k, a) => {
        println(s"\t$a")
        a.operations.foreach { case (kp, p) => println(s"\t\t$p")}
      }}
    }
    case CreateAccountOperation(accountId, label, amount) => {
      val account = accounts.get(accountId)
      if (account.isDefined) {
        persist(AccountOperationCreated(accountId, AccountOperation(UUID.randomUUID().toString, label, amount, DateTime.now()))) {
          case Success(evt) =>
            onEvent(evt)
            sender() ! CreateAccountOperationSuccess(account.get.balance)
          case Failure(err) =>
            sender() ! CreateAccountOperationFailure(err)
        }
      } else sender() ! CreateAccountOperationFailure(new IllegalArgumentException("Account not found"))
    }
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => accounts.put(account.id, AccountOperations(account.id, account.initialBalance))
    case AccountClosed(accountId) => accounts.remove(accountId)
    case AccountOperationCreated(accountId, operation) => {
      val account = accounts.get(accountId).get
      accounts.put(account.id, account.copy(balance = (account.balance + operation.amount), operations = account.operations + (operation.id -> operation)))
    }
  }
}