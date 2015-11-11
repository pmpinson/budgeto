package org.pmp.budgeto.domain

case class CommandFailure(message: String, cause: Option[Throwable] = None)