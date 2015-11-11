package org.pmp.budgeto.common

import com.rbmhtechnology.eventuate.EventsourcedView

trait ValidatorActor extends EventsourcedView {

  def validator(validator: Boolean, errorMessage: String): Option[Boolean] = if (validator) {
    sender() ! CommandFailure(errorMessage)
    None
  } else Some(true)

}