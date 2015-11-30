package org.pmp.budgeto.controller.filter

import play.api.Logger
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AccessFilter extends Filter {

  def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    val start = System.currentTimeMillis()

    val resultFuture = next(request)

    resultFuture.foreach(result => {
      Logger.warn(s"${request.remoteAddress} ${request.method} ${request.uri} ${result.header.status} ${System.currentTimeMillis() - start}")
    })

    resultFuture
  }

}
