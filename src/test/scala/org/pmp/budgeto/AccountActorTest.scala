package org.pmp.budgeto

import akka.actor.Props
import akka.pattern.ask
import org.pmp.budgeto.domain._
import org.scalatest.Matchers._

import scala.concurrent.Await
import scala.concurrent.duration._

class AccountActorTest extends EventuateContext {

  test("When I create an account") {
    Given("an account actor")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))

    When("send a create command")
    val future = accountActor ? CreateAccount("testAccount", "a note", 125)

    Then("I expected to have a create account success and the account have te good value")
    val CreateAccountSuccess(Account(id, label, note, initialBalance, _)) = waitFor(future)
    id should not be empty
    label should be("testAccount")
    note should be("a note")
    initialBalance should be(125D)
  }

  test("When I create an account but account with same label alread exist") {
    Given("an account actor and an account created with id 101")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))

    When("send a create account command")
    val future = accountActor ? CreateAccount("testAccount", "a dif note")

    Then("I expected to have an failure")
    val CommandFailure(message, _) = waitFor(future)
    message should be( """an account with label "testAccount" already exist""")
  }

  test("When I close an account") {
    Given("an account actor and an account created")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    val CreateAccountSuccess(Account(accountId, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))

    When("send a closed account command")
    val future = accountActor ? CloseAccount(accountId)

    Then("I expected to have a create account success and the account have te good value")
    val CloseAccountSuccess(Account(id, label, _, _, _)) = waitFor(future)
    id should be(accountId)
    label should be("testAccount")
  }

  test("When I close an account that not exist") {
    Given("an account actor and an account created")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    val CreateAccountSuccess(Account(accountId, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))

    When("send a closed account command but on id not existing")
    val future = accountActor ? CloseAccount("102")

    Then("I expected to have a create account success and the account have te good value")
    val CommandFailure(message, _) = waitFor(future)
    message should be( """account with id "102" not exist""")
  }

  test("When I close an account that is already closed") {
    Given("an account actor and an account closed with id 101")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    val CreateAccountSuccess(Account(accountId, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))
    waitFor(accountActor ? CloseAccount(accountId))

    When("send a closed account command")
    val future = accountActor ? CloseAccount(accountId)

    Then("I expected to have a create account success and the account have te good value")
    val CommandFailure(message, _) = waitFor(future)
    message should be( s"""account with id "$accountId" already closed""")
  }

}