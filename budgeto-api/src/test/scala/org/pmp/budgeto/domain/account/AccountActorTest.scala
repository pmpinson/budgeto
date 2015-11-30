package org.pmp.budgeto.domain.account

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import org.pmp.budgeto.domain.{ValidationException, CommandFailure, CommandSuccess}
import org.pmp.budgeto.{EventuateContext, EventuateTest}
import org.scalatest.Inspectors._
import org.scalatest.Matchers._

class AccountActorTest extends EventuateTest {

  var accountActor: ActorRef = null
  var accountId1: String = null
  var accountId2: String = null
  "An AccountActor" when {

    "create the actor and the account" when {

      "event have no errors" must {

        val system = newSystem
        val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
        val result = waitFor(actor ? CreateAccount("testAccount", "a note", 125))

        "store the account with good values" in {
          result shouldBe a[CommandSuccess]
          val CommandSuccess(AccountCreated(account)) = result

          account should matchPattern {
            case Account("testAccount", "a note", 125, _, _) =>
          }
        }

        "raise an event for others actors" in {
          result shouldBe a[CommandSuccess]
          val CommandSuccess(AccountCreated(account)) = result

          forExactly(1, expectedEvents(system)) { case AccountCreated(a) => a should be(account) }
        }
      }

      "event have errors" must {
        val system = newSystem
        val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))

        "raised an error if id is empty" in {
          val result = waitFor(actor ? CreateAccount("    ", "a note", 125))

          result shouldBe a[CommandFailure]
          val CommandFailure(message, cause) = result
          message should be("validation error")
          cause.get shouldBe a[ValidationException]
          val exception = cause.get.asInstanceOf[ValidationException]
          exception.field shouldBe "id"
          exception.message shouldBe "must not be empty"
        }
      }
    }

    "close account " when {

      val system = newSystem
      val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
      waitFor(actor ? CreateAccount("testAccount", "a note", 125))
      val result = waitFor(actor ? CloseAccount("testAccount"))

      "store the account closed with good values" in {
        result shouldBe a[CommandSuccess]
        val CommandSuccess(AccountClosed(accountId)) = result

        accountId should be ("testAccount")
      }

      "raise an event for others actors" in {
        result shouldBe a[CommandSuccess]
        val CommandSuccess(AccountClosed(accountId)) = result

        forExactly(1, expectedEvents(system)) { case AccountClosed(a) => a should be(accountId) }
      }
    }

    "create operation" when {

      "event have no errors" must {

        val system = newSystem
        val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
        waitFor(actor ? CreateAccount("testAccount", "a note", 125))
        val result = waitFor(actor ? CreateOperation("testAccount", "ope1", 12500))

        "store the operation with good values" in {
          result shouldBe a[CommandSuccess]
          val CommandSuccess(AccountOperationCreated(accountId, operation)) = result

          accountId should be("testAccount")
          operation should matchPattern {
            case AccountOperation(_, "ope1", 12500, _) =>
          }
        }

        "raise an event for others actors" in {
          result shouldBe a[CommandSuccess]
          val CommandSuccess(AccountOperationCreated(accountId, operation)) = result

          forExactly(1, expectedEvents(system)) { case AccountOperationCreated(a, b) => {
            a should be(accountId)
            b should be(operation)
          }
          }
        }
      }

      "event have errors" must {
        val system = newSystem
        val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
        waitFor(actor ? CreateAccount("testAccount", "a note", 125))

        "raised an error if label is empty" in {
          val result = waitFor(actor ? CreateOperation("testAccount", "    ", 12500))

          result shouldBe a[CommandFailure]
          val CommandFailure(message, cause) = result
          message should be("validation error")
          cause.get shouldBe a[ValidationException]
          val exception = cause.get.asInstanceOf[ValidationException]
          exception.field shouldBe "label"
          exception.message shouldBe "must not be empty"
        }

        "raised an error if amount is 0" in {
          val result = waitFor(actor ? CreateOperation("testAccount", "ope 1", 0))

          result shouldBe a[CommandFailure]
          val CommandFailure(message, cause) = result
          message should be("validation error")
          cause.get shouldBe a[ValidationException]
          val exception = cause.get.asInstanceOf[ValidationException]
          exception.field shouldBe "amount"
          exception.message shouldBe "must be different than 0"
        }
      }
    }

    "an account and an actor already exist for an id" must {

      val system = newSystem
      val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
      waitFor(actor ? CreateAccount("testAccount", "a note", 125))

      "return a failure if create a new actor with same id" in {
        val secondActor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
        val result = waitFor(secondActor ? CreateAccount("testAccount", "a note b", 1250))

        result shouldBe a[CommandFailure]
        val CommandFailure(message, cause) = result
        message should be("account 'testAccount' already created")
        cause shouldBe None
      }

      "return a success if create a new actor with different id" in {
        val secondActor = system.actorSystem.actorOf(Props(new AccountActor("testAccount2", system.eventLog)))
        val result = waitFor(secondActor ? CreateAccount("testAccount2", "a note b", 1250))

        result shouldBe a[CommandSuccess]
      }
    }

    "account is not created" must {

      val system = newSystem
      val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))

      "raise an error if close the account" in {
        val result = waitFor(actor ? CloseAccount("testAccount"))

        val CommandFailure(message, _) = result
        message should be("account 'testAccount' not created")
      }

      "raise an error if create an operation" in {
        val result = waitFor(actor ? CreateOperation("testAccount", "an operation", 12500))

        val CommandFailure(message, _) = result
        message should be("account 'testAccount' not created")
      }
    }

    "account is closed" must {

      val system = newSystem
      val actor = system.actorSystem.actorOf(Props(new AccountActor("testAccount", system.eventLog)))
      waitFor(actor ? CreateAccount("testAccount", "a note", 125))
      waitFor(actor ? CloseAccount("testAccount"))

      "raise an error if close the account" in {
        val result = waitFor(actor ? CloseAccount("testAccount"))

        val CommandFailure(message, _) = result
        message should be("account 'testAccount' is closed")
      }

      "raise an error if create an operation" in {
        val result = waitFor(actor ? CreateOperation("testAccount", "an operation", 12500))

        val CommandFailure(message, _) = result
        message should be("account 'testAccount' is closed")
      }
    }
  }
}