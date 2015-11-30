package org.pmp.budgeto.view.account

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedView
import org.pmp.budgeto.domain.ValidatorActor
import org.pmp.budgeto.domain.account._

import scala.collection.SortedSet

class AccountViewActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedView with ValidatorActor {

  private val accounts: scala.collection.mutable.Map[String, (Account, SortedSet[AccountOperation])] = scala.collection.mutable.Map.empty
  private val closedAccounts: scala.collection.mutable.Map[String, (Account, SortedSet[AccountOperation])] = scala.collection.mutable.Map.empty

  def allAccounts() = accounts ++ closedAccounts

  override val onCommand: Receive = {
    case PrintAccounts() => sender() ! PrintAccountsSuccess(accounts.values.map(a => a._1).toList)

    case PrintClosedAccounts() => sender() ! PrintClosedAccountsSuccess(closedAccounts.values.map(a => a._1).toList)

    case PrintAccount(accountId) => for {
      idExists <- Some(true) //idExists(accountId)
    } yield sender() ! PrintAccountSuccess(allAccounts.get(accountId).get._1)

    case PrintAccountOperations(accountId) => for {
      idExists <- Some(true) //idExists(accountId)
    } yield sender() ! PrintAccountOperationsSuccess(allAccounts.get(accountId).get._2)
  }

  //  def idExists(accountId: String): Option[Boolean] = validator(allAccounts.get(accountId).isEmpty, s"""account with id "${accountId}" not exist""")

  override val onEvent: Receive = {
    case AccountCreated(account) => accounts.put(account.id, (account, SortedSet[AccountOperation]()))
    case AccountClosed(accountId) => closedAccounts.put(accountId, accounts.remove(accountId).get)
    case AccountOperationCreated(accountId, operation) => {
      val accountInfo = accounts.get(accountId).get
      accounts.put(accountId, (accountInfo._1.copy(balance = accountInfo._1.balance + operation.amount), accountInfo._2 ++ Set(operation)))
    }
  }
}