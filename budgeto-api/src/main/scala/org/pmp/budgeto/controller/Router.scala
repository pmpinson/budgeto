package org.pmp.budgeto.controller

import org.pmp.budgeto.controller.filter.AccessFilter
import play.api.mvc._
import play.api.routing.sird._

class Router(isAliveCtrl: IsAliveCtrl, accountCtrl: AccountCtrl) {

  /**
   * the routes
   * @return
   */
  def routes: PartialFunction[RequestHeader, Handler] = {
    case GET(p"/isalive") => defaultFiltering(isAliveCtrl.alive)

    case GET(p"/account" ? q_?"state=$state") => defaultFiltering(accountCtrl.accounts(state))
    case GET(p"/account/$id") => defaultFiltering(accountCtrl.account(id))
  }

  /**
   * common filter for all routes
   * @param action
   * @return
   */
  def defaultFiltering(action: EssentialAction): EssentialAction = {
    AccessFilter {
      action
    }
  }


}
