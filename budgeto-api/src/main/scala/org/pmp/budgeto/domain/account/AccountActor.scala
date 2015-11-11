package org.pmp.budgeto.domain.account

import java.util.UUID

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor
import org.joda.time.DateTime
import org.pmp.budgeto.domain.{ValidatorActor, CommandFailure, PersistedActor}

class AccountActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedActor with ValidatorActor with PersistedActor {

  private val accounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty
  private var labels: List[String] = List.empty
  private var closedAccountIds: List[String] = List.empty

  override val onCommand: Receive = {

    case CreateAccount(label, note, initialBalance) => {
      for {
        labelNotEmpty <- validator(label.trim.isEmpty, s"label must not be empty")
        labelNotExists <- labelNotExists(label)
        account = Account(UUID.randomUUID().toString, label, note, initialBalance, initialBalance, DateTime.now())
      } yield persistAndSend(AccountCreated(account), CreateAccountSuccess(account))
    }

    case CloseAccount(accountId) => {
      for {
        exists <- accountExists(accountId)
        notClosed <- accountNotClosed(accountId)
        account <- accounts.get(accountId)
      } yield persistAndSend(AccountClosed(account), CloseAccountSuccess(account), CommandFailure)
    }

    case CreateAccountOperation(accountId, label, amount) => {
      for {
        labelNotEmpty <- validator(label.trim.isEmpty, s"label must not be empty")
        exists <- accountExists(accountId)
        notClosed <- accountNotClosed(accountId)
        accountOperation = AccountOperation(UUID.randomUUID().toString, label, amount, DateTime.now())
      } yield persistAndSend(AccountOperationCreated(accountId, accountOperation), CreateAccountOperationSuccess(accountId, accountOperation))
    }
  }

  def labelNotExists(label: String): Option[Boolean] = validator(labels.contains(label), s"""an account with label "${label}" already exist""")

  def accountExists(accountId: String): Option[Boolean] = validator(!accounts.contains(accountId), s"""account with id "${accountId}" not exist""")

  def accountNotClosed(accountId:String): Option[Boolean] = validator(closedAccountIds.contains(accountId), s"""account with id "${accountId}" is closed""")

  override val onEvent: Receive = {
    case AccountCreated(account) => {
      labels = labels :+ account.label
      accounts.put(account.id, account)
    }
    case AccountClosed(account) => closedAccountIds = closedAccountIds :+ account.id
    case AccountOperationCreated(_, _) => {}
  }

}