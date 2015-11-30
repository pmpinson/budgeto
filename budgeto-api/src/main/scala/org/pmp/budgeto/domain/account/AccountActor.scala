package org.pmp.budgeto.domain.account

import java.util.UUID

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor
import org.joda.time.DateTime
import org.pmp.budgeto.common.ValidatorActor
import org.pmp.budgeto.domain.{ValidatorActor, PersistedActor, CommandFailure}

trait AccountCommand {
  val accountId: String
}

case class CreateAccount(val accountId: String, note: String, initialBalance: Double = 0) extends AccountCommand

case class CloseAccount(val accountId: String) extends AccountCommand

case class CreateOperation(val accountId: String, label: String, amount: Double) extends AccountCommand

case class AccountCreated(account: Account)

case class AccountClosed(accountId: String)

case class AccountOperationCreated(accountId: String, operation: AccountOperation)

class AccountActor(val accountId: String, override val eventLog: ActorRef) extends EventsourcedActor with ValidatorActor with PersistedActor {

  override val id = s"account-${accountId}"
  override val aggregateId = Some(accountId)

  private var account: Account = _
  private var state: Option[AccountState] = None

  override val onCommand: Receive = {
    case event: CreateAccount => {
      state match {
        case None => createAccount(event)
        // reactivate account
        case Some(AccountStateOpened) => sender() ! CommandFailure(s"account '${event.accountId}' already created")
      }
    }
    case event: CloseAccount => {
      state match {
        case None => sender() ! CommandFailure(s"account '${event.accountId}' not created")
        case Some(AccountStateOpened) => closeAccount(event)
        case Some(AccountStateClosed) => sender() ! CommandFailure(s"account '${event.accountId}' is closed")
      }
    }
    case event: CreateOperation => {
      state match {
        case None => sender() ! CommandFailure(s"account '${event.accountId}' not created")
        case Some(AccountStateOpened) => createOperation(event)
        case Some(AccountStateClosed) => sender() ! CommandFailure(s"account '${event.accountId}' is closed")
      }
    }
  }

  def createAccount(event: CreateAccount): Unit = {
    val newAccount = Account(event.accountId, event.note, event.initialBalance, event.initialBalance, DateTime.now())
    for {
      idNotEmpty <- validator[Account](newAccount, _.id.trim.isEmpty, "id", "must not be empty")
    } yield persistAndSend(AccountCreated(newAccount))
  }

  def closeAccount(event: CloseAccount): Unit = persistAndSend(AccountClosed(event.accountId))

  def createOperation(event: CreateOperation): Unit = {
    val newOperation = AccountOperation(UUID.randomUUID().toString, event.label, event.amount, DateTime.now())
    for {
      labelNotEmpty <- validator[AccountOperation](newOperation, _.label.trim.isEmpty, "label", "must not be empty")
      amount0 <- validator[AccountOperation](newOperation, _.amount == 0, "amount", "must be different than 0")
    } yield persistAndSend(AccountOperationCreated(event.accountId, newOperation))
  }

  override val onEvent: Receive = {
    case event@AccountCreated(newAccount) => {
      account = newAccount
      state = Some(AccountStateOpened)
    }
    case event: AccountClosed => state = Some(AccountStateClosed)
    case event: AccountOperationCreated => {}
  }

}