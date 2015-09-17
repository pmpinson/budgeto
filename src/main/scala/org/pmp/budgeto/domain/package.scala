package org.pmp.budgeto

import com.rbmhtechnology.eventuate.EventsourcedActor

import scala.util.{Failure, Success}

package object domain {

  case class CommandFailure(message: String, cause: Option[Throwable] = None)

  trait EventuateActor extends EventsourcedActor {

    def persistEvent[OBJ](event: Any, onSuccess: Any, onFailure: (String, Option[Throwable]) => Any) = {
      persist(event) {
        case Success(e) =>
          onEvent(e)
          sender() ! onSuccess
        case Failure(err) =>
          sender() ! onFailure(err.getMessage, Some(err))
      }
    }

  }

}
