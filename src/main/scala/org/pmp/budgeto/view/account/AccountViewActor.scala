package org.pmp.budgeto.view.account

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedView
import org.pmp.budgeto.domain.CommandFailure
import org.pmp.budgeto.domain.account._

import scala.collection.SortedSet

class AccountViewActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedView {

  private val accounts: scala.collection.mutable.Map[String, (Account, SortedSet[AccountOperation])] = scala.collection.mutable.Map.empty
  private val closedAccounts: scala.collection.mutable.Map[String, (Account, SortedSet[AccountOperation])] = scala.collection.mutable.Map.empty

  def allAccounts() = accounts ++ closedAccounts

  override val onCommand: Receive = {
    case PrintAccounts() => sender() ! PrintAccountsSuccess(accounts.values.map(a => a._1).toList)

    case PrintClosedAccounts() => sender() ! PrintClosedAccountsSuccess(closedAccounts.values.map(a => a._1).toList)

    case PrintAccountOperations(accountId) => for {
      idNotExist <- if (allAccounts.get(accountId).isEmpty) {
        sender() ! CommandFailure( s"""account with id "${accountId}" not exist""")
        None
      } else Some(true)
    } yield sender() ! PrintAccountOperationsSuccess(allAccounts.get(accountId).get._2)
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => accounts.put(account.id, (account, SortedSet[AccountOperation]()))
    case AccountClosed(account) => closedAccounts.put(account.id, accounts.remove(account.id).get)
    case AccountOperationCreated(accountId, operation) => {
      val accountInfo = accounts.get(accountId).get
      accounts.put(accountId, (accountInfo._1.copy(balance = accountInfo._1.balance + operation.amount), accountInfo._2 ++ Set(operation)))
    }
  }
}