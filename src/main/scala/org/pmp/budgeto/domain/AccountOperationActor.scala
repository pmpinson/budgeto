package org.pmp.budgeto.domain

import java.util.UUID

import akka.actor.{ActorRef, Props}
import com.rbmhtechnology.eventuate.EventsourcedActor
import org.joda.time.DateTime

import scala.collection.SortedSet
import scala.util.{Failure, Success}

/**
 * Objects
 */
case class AccountOperation(id: String, label: String, amount: Double, date: DateTime) extends Ordered[AccountOperation] {
  override def compare(that: AccountOperation): Int = date.compareTo(that.date)
}

case class AccountOperations(id: String, balance: Double, operations: Map[String, AccountOperation] = Map.empty)

/**
 * list of commands
 */
case class PrintAccountsOperations()

case class CreateAccountOperation(accountId: String, label: String, n: Double)

/**
 * replies on command
 */
case class CreateAccountOperationSuccess(accountId: String, operation: AccountOperation)

case class CreateAccountOperationFailure(cause: Throwable)

/**
 * Events
 */
case class AccountOperationCreated(accountId: String, operation: AccountOperation)

class AccountOperationActor(override val id: String, override val eventLog: ActorRef) extends EventuateActor {

  private var accounts: List[String] = List.empty

  override val onCommand: Receive = {
    case CreateAccountOperation(accountId, label, amount) => {
      for {
        idNotExist <- if (!accounts.contains(accountId)) {
          sender() ! CommandFailure( s"""account with id "${accountId}" not exist""")
          None
        } else Some(true)
        accountOperation = AccountOperation(UUID.randomUUID().toString, label, amount, DateTime.now())
      } yield persistAndSend(AccountOperationCreated(accountId, accountOperation), CreateAccountOperationSuccess(accountId, accountOperation))
    }
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => accounts = accounts :+ account.id
    case AccountClosed(account) => accounts = accounts diff List(account.id)
    case AccountOperationCreated(_, _) => {}
  }
}