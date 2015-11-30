package org.pmp.budgeto.domain

import com.rbmhtechnology.eventuate.EventsourcedActor

import scala.util.{Failure, Success}

trait PersistedActor extends EventsourcedActor {

  def persistAndSend(eventToPersist: Any, onSuccess: (Any) => Any = CommandSuccess, onFailure: (String, Option[Throwable]) => Any = CommandFailure): Unit = {
    persist(eventToPersist) {
      case Success(e) =>
        onEvent(e)
        sender() ! onSuccess(eventToPersist)
      case Failure(err) =>
        sender() ! onFailure(err.getMessage, Some(err))
    }
  }

}