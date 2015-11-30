package org.pmp.budgeto.domain

import com.rbmhtechnology.eventuate.EventsourcedView

trait ValidatorActor extends EventsourcedView {

  def validator[T](obj: T, validator: (T) => Boolean, field: String, message: String): Option[T] = if (validator(obj)) {
    sender() ! CommandFailure("validation error", Some(new ValidationException(field, message)))
    None
  } else Some(obj)

}

class ValidationException(val field: String, val message: String) extends Exception(field + " - " + message)