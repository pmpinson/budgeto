package org.pmp.budgeto.domain

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
case class CreateAccount(label: String, note: String, initialBalance: Double = 0)

case class CloseAccount(accountId: String)

/**
 * replies on command
 */
case class CreateAccountSuccess(account: Account)

case class CreateAccountFailure(message: String, cause: Option[Throwable] = None)

case class CloseAccountSuccess(account: Account)

case class CloseAccountFailure(message: String, cause: Option[Throwable] = None)

/**
 * Events
 */
case class AccountCreated(account: Account)

case class AccountClosed(account: Account)


class AccountActor(override val id: String, override val eventLog: ActorRef, idGenerator: () => String = new IdGenerator().next) extends EventsourcedActor {

  private val accounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty
  private var labels: List[String] = List.empty
  private var closedIds: List[String] = List.empty

  def persistObject[T](obj: T, event: (T) => Any, onSuccess: (T) => Any, onFailure: (String, Option[Throwable]) => Any) = {
    persist(event(obj)) {
      case Success(e) =>
        onEvent(e)
        sender() ! onSuccess(obj)
      case Failure(err) =>
        sender() ! onFailure(err.getMessage, Some(err))
    }
  }

  override val onCommand: Receive = {

    case CreateAccount(label, note, initialBalance) => {
      for {
        labelExist <- if (labels.contains(label)) {
          sender() ! CreateAccountFailure( s"""an account with label "${label}" already exist""")
          None
        } else Some(true)
        account = Account(idGenerator(), label, note, initialBalance, DateTime.now())
      } yield persistObject(account, AccountCreated, CreateAccountSuccess, CreateAccountFailure)
    }

    case CloseAccount(accountId) => {
      for {
        idNotExist <- if (accounts.get(accountId).isEmpty) {
          sender() ! CloseAccountFailure( s"""account with id "${accountId}" not exist""")
          None
        } else Some(true)
        alreadyClose <- if (closedIds.contains(accountId)) {
          sender() ! CloseAccountFailure( s"""account with id "${accountId}" already closed""")
          None
        } else Some(true)
        account <- accounts.get(accountId)
      } yield persistObject(account, AccountClosed, CloseAccountSuccess, CloseAccountFailure)
    }
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => {
      labels = labels :+ account.label
      accounts.put(account.id, account)
    }
    case AccountClosed(account) => {
      closedIds = closedIds :+ account.id
    }
  }

}