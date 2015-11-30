package org.pmp.budgeto

import org.pmp.budgeto.domain.account.Account
import play.api.libs.json._
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.util.Try

package object controller {

  implicit val accountJsonFormat = Json.format[Account]

  /*def parsedJsontoTry[T](result: JsResult[T]): Try[T] = result match {
    case s: JsSuccess[T] => Success(s.get)
    case e: JsError => {
      val errors = for (
        error <- e.errors;
        field = error._1;
        fieldErrors = error._2.map(_.message)
      ) yield ValidationError(field.toString.substring(1), fieldErrors(0))
      Failure(new MultipleValidationError("error converting object to json", errors.toList))
    }
  }

  def parseDate(date: String): Try[DateTime] = Try(DateTime.parse(date, DateTimeFormat.forPattern(ConfigConstants.datePattern)))
  */

  def managedError(result: Try[Result]) = {
    result.recover {
      //case error: ValidationError => BadRequest(Json.toJson(new MultipleValidationError("error validating object", List(error))))
      //case error: MultipleValidationError => BadRequest(Json.toJson(error))
      //case error: NotFoundError => NotFound(Json.toJson(error))
      case error: IllegalArgumentException => BadRequest
    }.getOrElse(InternalServerError)
  }
}
