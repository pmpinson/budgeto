package org.pmp.budgeto

package object domain {

  case class CommandFailure(message: String, cause: Option[Throwable] = None)

}
