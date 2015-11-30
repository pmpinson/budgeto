package org.pmp.budgeto

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.joda.time.DateTime
import org.pmp.budgeto.domain.PersistedActor
import org.pmp.budgeto.view.account.{ListEvents, ListEventsSuccess, ExpectedEventActor}
import org.scalatest.{BeforeAndAfter, FunSuite, GivenWhenThen}

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}

/**
 * trait to simplify write of test for Envantuate actor
 */
trait EventuateContext extends FunSuite with BeforeAndAfter with GivenWhenThen {
  implicit var system: ActorSystem = null
  implicit var timeout: Timeout = null
  var eventLog: ActorRef = null
  var logId: String = null
  var expectedEventActor: ActorRef = null

  def setup = {}

  before {
    system = ActorSystem("budgetoTest")
    timeout = Timeout(10.seconds)
    logId = DateTime.now().getMillis.toString
    eventLog = system.actorOf(LeveldbEventLog.props(logId = "budgetoTest" + logId, prefix = "test"))
    expectedEventActor = system.actorOf(Props(new ExpectedEventActor(eventLog)))
    setup
  }

  after {
    system.terminate()
  }

  def actorId(name: String): String = name + logId

  def waitFor(future: Future[Any]): Any = Await.result(future, Duration.Inf)

  def expectedEvents(): List[Any] = {
    val ListEventsSuccess(events) = waitFor(expectedEventActor ? ListEvents())
    events
  }

}