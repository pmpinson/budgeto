package org.pmp.budgeto

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import org.pmp.budgeto.domain._
import org.scalatest.Inspectors._
import org.scalatest.Matchers._

class AccountOperationActorTest extends EventuateContext {

  var accountActor: ActorRef = null
  var accountOperationActor: ActorRef = null
  var accountId1: String = null
  var accountId2: String = null

  override def setup = {
    accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    accountOperationActor = system.actorOf(Props(new AccountOperationActor(actorId("AccountActor"), eventLog)))

    val CreateAccountSuccess(Account(resAccountId1, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount", "a note", 125))
    accountId1 = resAccountId1
    val CreateAccountSuccess(Account(resAccountId2, _, _, _, _, _)) = waitFor(accountActor ? CreateAccount("testAccount2", "a note2", 2125))
    accountId2 = resAccountId2
    waitFor(accountActor ? CloseAccount(accountId2))
    waitFor(accountActor ? CreateAccount("testAccount3", "bzzzzzz"))
  }

  test("When I made a operation on an unexisting account") {
    Given("an account actor, and an account view actor, and 3 accounts created but 1 closed")

    When("send a new operation on unexisting account")
    val future = accountOperationActor ? CreateAccountOperation("102", "an operation", 12500)

    Then("I expected to have a faillure")
    val CommandFailure(message, _) = waitFor(future)
    message should be( """account with id "102" not exist""")
  }

  test("When I made a operation on a closed account") {
    Given("an account actor, and an account view actor, and 3 accounts created but 1 closed")

    When("send a new operation on closed account")
    val future = accountOperationActor ? CreateAccountOperation(accountId2, "an operation", 12500)

    Then("I expected to have a faillure")
    val CommandFailure(message, _) = waitFor(future)
    message should be( s"""account with id "$accountId2" not exist""")
  }

  test("When I made a operation on a existing non closed account") {
    Given("an account actor, and an account view actor, and 3 accounts created but 1 closed")

    When("send a new operation on closed account")
    val future = accountOperationActor ? CreateAccountOperation(accountId1, "an operation", 12500)

    Then("I expected to have a faillure")
    val CreateAccountOperationSuccess(accountId, accountOperation) = waitFor(future)
    accountId should be (accountId1)
    accountOperation should matchPattern {
      case AccountOperation(_, "an operation", 12500, _) =>
    }
    Then("a event with the operation have been sent")
    forExactly(1, expectedEvents) { case AccountOperationCreated(id, ope) => {
      id should be (accountId1)
      ope should be(accountOperation)
    }}
  }

}