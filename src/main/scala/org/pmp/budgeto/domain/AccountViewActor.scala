package org.pmp.budgeto.domain

import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedActor

/**
 * Objects
 */

/**
 * list of commands
 */
case class PrintAccounts()

case class PrintClosedAccounts()

/**
 * replies on command
 */
case class PrintAccountsSuccess(accounts: List[Account])
case class PrintClosedAccountsSuccess(accounts: List[Account])

/**
 * Events
 */

class AccountViewActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedActor {

  private val accounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty
  private val closedAccounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty

  def gg() = "ss"

  override val onCommand: Receive = {
    case PrintAccounts() => sender() ! PrintAccountsSuccess(accounts.values.toList)
    case PrintClosedAccounts() => sender() ! PrintAccountsSuccess(closedAccounts.values.toList)
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => {
      accounts.put(account.id, account)
    }
    case AccountClosed(account) => {
      accounts.remove(id)
      closedAccounts.put(account.id, account)
    }
  }
}