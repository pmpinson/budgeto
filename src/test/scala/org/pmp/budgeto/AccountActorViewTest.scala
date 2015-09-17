package org.pmp.budgeto

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import org.pmp.budgeto.domain._
import org.scalatest.Inspectors._
import org.scalatest.Matchers._

class AccountActorViewTest extends EventuateContext {

  var accountActor: ActorRef = null
  var accountViewActor: ActorRef = null

  override def setup = {
    accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    accountViewActor = system.actorOf(Props(new AccountViewActor(actorId("AccountActor"), eventLog)))

    waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))
    val CreateAccountSuccess(Account(accountId, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount2", "a note2", 2125))
    waitFor(accountActor ? CloseAccount(accountId))
    waitFor(accountActor ? CreateAccount("testAccount3", "bzzzzzz"))
  }

  test("ask to list active account") {
    Given("an account actor, and an account view actor, and 3 account created")

    When("send a print command")
    val future = accountViewActor.actorRef ? PrintAccounts()

    Then("I expected to have a list of 2 accounts and empty for closed accounts")
    val PrintAccountsSuccess(accounts) = waitFor(future)
    accounts should have size 2
    forAll(accounts) { x => x should matchPattern {
      case Account(_, "testAccount", "a note", 125, _) =>
      case Account(_, "testAccount3", "bzzzzzz", 0, _) =>
    }
    }
  }

  test("ask to list all closed account") {
    Given("an account actor, and an account view actor, and 2 account created with one closed")

    When("send a print closed command")
    val future = accountViewActor.actorRef ? PrintClosedAccounts()

    Then("I expected to have a list of 2 accounts and empty for closed accounts")
    val PrintClosedAccountsSuccess(accounts) = waitFor(future)
    accounts should have size 1
    forAll(accounts) { x => x should matchPattern {
      case Account(_, "testAccount2", "a note2", 2125, _) =>
    }
    }
  }

}