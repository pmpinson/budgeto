package org.pmp.budgeto.view.account

import akka.actor.ActorRef
import org.pmp.budgeto.domain.PersistedActor

case class ListEvents()

case class ListEventsSuccess(events: List[Any])

class ExpectedEventActor(override val eventLog: ActorRef) extends PersistedActor {

  var events: List[Any] = List.empty
  val id:String = "ExpectedEvent"

  override val onCommand: Receive = {
    case ListEvents() => sender() ! ListEventsSuccess(events)
  }

  override val onEvent: Receive = {
    case e => events = events :+ e
  }
}
