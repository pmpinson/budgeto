package org.pmp.budgeto.controller

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import org.pmp.budgeto.view.account._
import play.api.libs.json._
import play.api.mvc.{Action, Controller, EssentialAction}

import scala.concurrent.ExecutionContext.Implicits.global

class AccountCtrl(val accountViewActor: ActorRef)(implicit val timeout: Timeout) extends Controller {

  def accounts(state: Option[String]): EssentialAction = Action.async {
    state match {
      case Some("close") => accountViewActor ? PrintClosedAccounts() map { case PrintClosedAccountsSuccess(accounts) => Ok(Json.toJson(accounts)) }
      //      case Some("all") => Promise.sequence(List(
      //        accountViewActor ? PrintClosedAccounts() map { case PrintClosedAccountsSuccess(accounts) => Ok(Json.toJson(accounts))},
      //        accountViewActor ? PrintAccounts() map { case PrintAccountsSuccess(accounts) => Ok(Json.toJson(accounts))}
      //      ))
      case _ => accountViewActor ? PrintAccounts() map { case PrintAccountsSuccess(accounts) => Ok(Json.toJson(accounts)) }
    }
  }

  def account(id: String): EssentialAction = Action.async {
    accountViewActor ? PrintAccount(id) map { case PrintAccountSuccess(account) => Ok(Json.toJson(account)) }
  }

}
