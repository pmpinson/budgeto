package org.pmp.budgeto

import com.rbmhtechnology.eventuate.EventsourcedActor

import scala.util.{Failure, Success}

package object domain {

  case class CommandFailure(message: String, cause: Option[Throwable] = None)

  trait EventuateActor extends EventsourcedActor {

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

}
