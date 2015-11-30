package org.pmp.budgeto.domain

case class CommandSuccess(event: Any)

case class CommandFailure(message: String, cause: Option[Throwable] = None)