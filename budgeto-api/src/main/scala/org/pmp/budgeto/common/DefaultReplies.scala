package org.pmp.budgeto.common

case class CommandFailure(message: String, cause: Option[Throwable] = None)