package org.pmp.budgeto.controller

import play.api.mvc.Results.Ok
import play.api.mvc.{Action, EssentialAction}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

class IsAliveCtrl {

  def alive: EssentialAction = Action.async {
    Future {
      managedError(
        Try(Ok)
      )
    }
  }

}
