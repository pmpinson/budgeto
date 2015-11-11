package org.pmp.budgeto.domain

import com.rbmhtechnology.eventuate.{EventsourcedView, EventsourcedActor}

import scala.util.{Failure, Success}

trait ValidatorActor extends EventsourcedView {

  def validator(validator: Boolean, errorMessage: String): Option[Boolean] = if (validator) {
    sender() ! CommandFailure(errorMessage)
    None
  } else Some(true)

}