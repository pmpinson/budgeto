package org.pmp.budgeto.domain

import java.util.UUID

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor
import org.joda.time.DateTime

import scala.util.{Failure, Success}

/**
 * Objects
 */
case class Account(id: String, label: String, note: String, initialBalance: Double, balance: Double, creationDate: DateTime)

case class AccountOperation(id: String, label: String, amount: Double, date: DateTime) extends Ordered[AccountOperation] {
  override def compare(that: AccountOperation): Int = date.compareTo(that.date)
}

/**
 * list of commands
 */
case class CreateAccount(label: String, note: String, initialBalance: Double = 0)

case class CloseAccount(accountId: String)

case class CreateAccountOperation(accountId: String, label: String, n: Double)

/**
 * replies on command
 */
case class CreateAccountSuccess(account: Account)

case class CloseAccountSuccess(account: Account)

case class CreateAccountOperationSuccess(accountId: String, operation: AccountOperation)

/**
 * Events
 */
case class AccountCreated(account: Account)

case class AccountClosed(account: Account)

case class AccountOperationCreated(accountId: String, operation: AccountOperation)

class AccountActor(override val id: String, override val eventLog: ActorRef) extends EventuateActor {

  private val accounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty
  private var labels: List[String] = List.empty
  private var closedAccountIds: List[String] = List.empty

  override val onCommand: Receive = {

    case CreateAccount(label, note, initialBalance) => {
      for {
        labelExist <- if (labels.contains(label)) {
          sender() ! CommandFailure( s"""an account with label "${label}" already exist""")
          None
        } else Some(true)
        account = Account(UUID.randomUUID().toString, label, note, initialBalance, initialBalance, DateTime.now())
      } yield persistAndSend(AccountCreated(account), CreateAccountSuccess(account))
    }

    case CloseAccount(accountId) => {
      for {
        idNotExist <- if (accounts.get(accountId).isEmpty) {
          sender() ! CommandFailure( s"""account with id "${accountId}" not exist""")
          None
        } else Some(true)
        alreadyClose <- if (closedAccountIds.contains(accountId)) {
          sender() ! CommandFailure( s"""account with id "${accountId}" is already closed""")
          None
        } else Some(true)
        account <- accounts.get(accountId)
      } yield persistAndSend(AccountClosed(account), CloseAccountSuccess(account), CommandFailure)
    }

    case CreateAccountOperation(accountId, label, amount) => {
      for {
        idNotExist <- if (!accounts.contains(accountId)) {
          sender() ! CommandFailure( s"""account with id "${accountId}" not exist""")
          None
        } else Some(true)
        alreadyClose <- if (closedAccountIds.contains(accountId)) {
          sender() ! CommandFailure( s"""account with id "${accountId}" is closed""")
          None
        } else Some(true)
        accountOperation = AccountOperation(UUID.randomUUID().toString, label, amount, DateTime.now())
      } yield persistAndSend(AccountOperationCreated(accountId, accountOperation), CreateAccountOperationSuccess(accountId, accountOperation))
    }
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => {
      labels = labels :+ account.label
      accounts.put(account.id, account)
    }
    case AccountClosed(account) => closedAccountIds = closedAccountIds :+ account.id
    case AccountOperationCreated(_, _) => {}
  }

}