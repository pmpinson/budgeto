package org.pmp.budgeto

import com.rbmhtechnology.eventuate.EventsourcedActor

import scala.util.{Failure, Success}

package object domain {

  case class CommandFailure(message: String, cause: Option[Throwable] = None)

  trait EventuateActor extends EventsourcedActor {

    def persistEvent[T](obj: T, event: (T) => Any, onSuccess: (T) => Any, onFailure: (String, Option[Throwable]) => Any) = {
      persist(event(obj)) {
        case Success(e) =>
          onEvent(e)
          sender() ! onSuccess(obj)
        case Failure(err) =>
          sender() ! onFailure(err.getMessage, Some(err))
      }
    }

  }

}
