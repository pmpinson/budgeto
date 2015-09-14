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

/**
 * Events
 */

class AccountViewActor(override val id: String, override val eventLog: ActorRef) extends EventsourcedActor {

  private val accounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty
  private val closedAccounts: scala.collection.mutable.Map[String, Account] = scala.collection.mutable.Map.empty

  override val onCommand: Receive = {
    case PrintAccounts => {
      println("accounts")
      accounts.foreach { case (k, a) => println(s"\t$a") }
    }
    case PrintAccounts => {
      println("closedAccounts")
      closedAccounts.foreach { case (k, a) => println(s"\t$a") }
    }
  }

  override val onEvent: Receive = {
    case AccountCreated(account) => accounts.put(account.id, account)
    case AccountClosed(account) => closedAccounts.put(account.id, account);accounts.remove(id)
  }
}