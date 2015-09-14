package org.pmp.budgeto

import akka.actor.Props
import akka.pattern.ask
import org.pmp.budgeto.domain._
import org.scalatest.GivenWhenThen
import org.scalatest.Matchers._

import scala.concurrent.Await
import scala.concurrent.duration._

class AccountActorTest extends EventuateContext with GivenWhenThen {

  test("When I create an account") {
    Given("an account actor")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))

    When("send a create command")
    val future = accountActor ? CreateAccount("testAccount", "a note", 125)

    Then("I expected to have a create account success and the account have te good value")
    val CreateAccountSuccess(Account(id, label, note, initialBalance, _)) = Await.result(future, Duration.Inf)
    id should not be empty
    label should be("testAccount")
    note should be("a note")
    initialBalance should be(125D)
  }

  test("When I create an account but account with same label alread exist") {
    Given("an account actor and an account created with id 101")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog)))
    accountActor ? CreateAccount("testAccount", "a note", 125)

    When("send a create account command")
    val future = accountActor ? CreateAccount("testAccount", "a dif note")

    Then("I expected to have an failure")
    val CreateAccountFailure(message, _) = Await.result(future, Duration.Inf)
    message should be( """an account with label "testAccount" already exist""")
  }

  test("When I close an account") {
    Given("an account actor and an account created with id 101")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog, new IdGeneratorFixed("101").next)))
    accountActor ! CreateAccount("testAccount", "a note", 125)

    When("send a closed account command")
    val future = accountActor ? CloseAccount("101")

    Then("I expected to have a create account success and the account have te good value")
    val CloseAccountSuccess(Account(id, label, _, _, _)) = Await.result(future, Duration.Inf)
    id should be("101")
    label should be("testAccount")
  }

  test("When I close an account that not exist") {
    Given("an account actor and an account created with id 101")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog, new IdGeneratorFixed("101").next)))
    accountActor ! CreateAccount("testAccount", "a note", 125)

    When("send a closed account command")
    val future = accountActor ? CloseAccount("102")

    Then("I expected to have a create account success and the account have te good value")
    val CloseAccountFailure(message, _) = Await.result(future, Duration.Inf)
    message should be( """account with id "102" not exist""")
  }

  test("When I close an account that is already closed") {
    Given("an account actor and an account closed with id 101")
    val accountActor = system.actorOf(Props(new AccountActor(actorId("AccountActor"), eventLog, new IdGeneratorFixed("101").next)))
    accountActor ? CreateAccount("testAccount", "a note", 125)
    accountActor ? CloseAccount("101")

    When("send a closed account command")
    val future = accountActor ? CloseAccount("101")

    Then("I expected to have a create account success and the account have te good value")
    val CloseAccountFailure(message, _) = Await.result(future, Duration.Inf)
    message should be( """account with id "101" already closed""")
  }

}