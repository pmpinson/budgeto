package org.pmp.budgeto

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.joda.time.DateTime
import org.pmp.budgeto.domain.PersistedActor
import org.pmp.budgeto.view.account.{ListEventsSuccess, ListEvents, ExpectedEventActor}
import org.scalatest._

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}

case class EventuateSystem(val actorSystem: ActorSystem, val eventLog: ActorRef, val expectedEventActor:ActorRef) {
  def terminate = actorSystem.terminate()
}

/**
 * trait to simplify write of test for Envantuate actor
 */
trait EventuateTest extends WordSpec {

  implicit val timeout = Timeout(10.seconds)

  def newSystem:EventuateSystem = {
    val actorSystem = ActorSystem("budgetoTest")
    val logId = DateTime.now().getMillis.toString
    val eventLog = actorSystem.actorOf(LeveldbEventLog.props(logId = "budgetoTest" + logId, prefix = "test"))
    val expectedEventActor = actorSystem.actorOf(Props(new ExpectedEventActor(eventLog)))
    val system = EventuateSystem(actorSystem, eventLog, expectedEventActor)

    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run(): Unit = system.terminate
    })
    system
  }

  def waitFor(future: Future[Any]): Any = Await.result(future, Duration.Inf)

  def expectedEvents(system: EventuateSystem): List[Any] = {
    val ListEventsSuccess(events) = waitFor(system.expectedEventActor ? ListEvents())
    events
  }

}
