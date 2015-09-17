package org.pmp.budgeto

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import org.pmp.budgeto.domain._
import org.scalatest.Inspectors._
import org.scalatest.Matchers._

class AccountActorTest extends EventuateContext {

  var accountActor: ActorRef = null

  override def setup = {
    accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
  }

  test("When I create an account") {
    Given("an account actor")

    When("send a create command")
    val future = accountActor ? CreateAccount("testAccount", "a note", 125)

    Then("I expected to have a create account success, the account have te good value")
    val CreateAccountSuccess(account) = waitFor(future)
    account.id should not be empty
    account should matchPattern {
      case Account(_, "testAccount", "a note", 125, _, _) =>
    }
    Then("a event with the account have been sent")
    forExactly(1, expectedEvents) { case AccountCreated(a) => a should be(account) }
  }

  test("When I create an account but account with same label alread exist") {
    Given("an account actor and an account created")
    waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))

    When("send a create account command")
    val future = accountActor ? CreateAccount("testAccount", "a dif note")

    Then("I expected to have an failure")
    val CommandFailure(message, _) = waitFor(future)
    message should be( """an account with label "testAccount" already exist""")
  }

  test("When I close an account") {
    Given("an account actor and an account created")
    val CreateAccountSuccess(Account(accountId, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))

    When("send a closed account command")
    val future = accountActor ? CloseAccount(accountId)

    Then("I expected to have a closed account success")
    val CloseAccountSuccess(account) = waitFor(future)
    account should matchPattern {
      case Account(accountId, "testAccount", _, _, _, _) =>
    }
    Then("a event with the account have been sent")
    forExactly(1, expectedEvents) { case AccountClosed(a) => a should be(account) }
  }

  test("When I close an account that not exist") {
    Given("an account actor and an account created")
    val CreateAccountSuccess(Account(accountId, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))

    When("send a closed account command but on id not existing")
    val future = accountActor ? CloseAccount("102")

    Then("I expected to have a create account success and the account have te good value")
    val CommandFailure(message, _) = waitFor(future)
    message should be( """account with id "102" not exist""")
  }

  test("When I close an account that is already closed") {
    Given("an account actor and an account closed with id 101")
    val CreateAccountSuccess(Account(accountId, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))
    waitFor(accountActor ? CloseAccount(accountId))

    When("send a closed account command")
    val future = accountActor ? CloseAccount(accountId)

    Then("I expected to have a create account success and the account have te good value")
    val CommandFailure(message, _) = waitFor(future)
    message should be( s"""account with id "$accountId" already closed""")
  }

}