package org.pmp.budgeto.domain.account

import akka.actor.{ActorRef, Props}
import com.rbmhtechnology.eventuate.EventsourcedActor

// manage account id exist or not or id empty
class AccountManager(val replicaId: String, val eventLog: ActorRef) extends EventsourcedActor {

  private var accountActors: Map[String, ActorRef] = Map.empty

  override val id = s"s-om-$replicaId"

  override val onCommand: Receive = {
    case a: AccountCommand => accountActor(a.accountId) forward a
  }

  override val onEvent: Receive = {
    case _ => {}
  }

  private def accountActor(accountId: String): ActorRef = accountActors.get(accountId) match {
    case Some(accountActor) => accountActor
    case None =>
      accountActors = accountActors + (accountId -> context.actorOf(Props(new AccountActor(accountId, eventLog))))
      accountActors(accountId)
  }

}
