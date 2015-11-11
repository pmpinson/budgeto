package org.pmp.budgeto.domain.account

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import org.pmp.budgeto.EventuateContext
import org.pmp.budgeto.domain._
import org.scalatest.Inspectors._
import org.scalatest.Matchers._

class AccountActorTest extends EventuateContext {

  var accountActor: ActorRef = null
  var accountId1: String = null
  var accountId2: String = null

  override def setup = {
    accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
  }

  def createAccounts = {
    val CreateAccountSuccess(Account(resAccountId1, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))
    accountId1 = resAccountId1
    val CreateAccountSuccess(Account(resAccountId2, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount2", "a note2", 2125))
    accountId2 = resAccountId2
    waitFor(accountActor ? CloseAccount(accountId2))
    waitFor(accountActor ? CreateAccount("testAccount3", "bzzzzzz"))
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

  test("When I close an account that is closed") {
    Given("an account actor and an account closed with id 101")
    val CreateAccountSuccess(Account(accountId, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))
    waitFor(accountActor ? CloseAccount(accountId))

    When("send a closed account command")
    val future = accountActor ? CloseAccount(accountId)

    Then("I expected to have a create account success and the account have te good value")
    val CommandFailure(message, _) = waitFor(future)
    message should be( s"""account with id "$accountId" is closed""")
  }

  test("When I made a operation on an unexisting account") {
    Given("an account actor, and 3 accounts created but 1 closed")
    createAccounts

    When("send a new operation on unexisting account")
    val future = accountActor ? CreateAccountOperation("102", "an operation", 12500)

    Then("I expected to have a faillure")
    val CommandFailure(message, _) = waitFor(future)
    message should be( """account with id "102" not exist""")
  }

  test("When I made a operation on a closed account") {
    Given("an account actor, and 3 accounts created but 1 closed")
    createAccounts

    When("send a new operation on closed account")
    val future = accountActor ? CreateAccountOperation(accountId2, "an operation", 12500)

    Then("I expected to have a faillure")
    val CommandFailure(message, _) = waitFor(future)
    message should be( s"""account with id "$accountId2" is closed""")
  }

  test("When I made a operation on a existing non closed account") {
    Given("an account actor, and 3 accounts created but 1 closed")
    createAccounts

    When("send a new operation on closed account")
    val future = accountActor ? CreateAccountOperation(accountId1, "an operation", 12500)

    Then("I expected to have a faillure")
    val CreateAccountOperationSuccess(accountId, accountOperation) = waitFor(future)
    accountId should be(accountId1)
    accountOperation should matchPattern {
      case AccountOperation(_, "an operation", 12500, _) =>
    }
    Then("a event with the operation have been sent")
    forExactly(1, expectedEvents) { case AccountOperationCreated(id, ope) => {
      id should be(accountId1)
      ope should be(accountOperation)
    }
    }
  }

}