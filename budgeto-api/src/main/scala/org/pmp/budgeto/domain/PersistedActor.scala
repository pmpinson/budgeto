package org.pmp.budgeto.domain

import com.rbmhtechnology.eventuate.EventsourcedActor

import scala.util.{Failure, Success}

trait PersistedActor extends EventsourcedActor {

  def persistAndSend[OBJ](eventToPersist: Any, eventToSendOnSuccess: Any, onFailure: (String, Option[Throwable]) => Any = CommandFailure) = {
    persist(eventToPersist) {
      case Success(e) =>
        onEvent(e)
        sender() ! eventToSendOnSuccess
      case Failure(err) =>
        sender() ! onFailure(err.getMessage, Some(err))
    }
  }

}