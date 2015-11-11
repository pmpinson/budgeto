package org.pmp

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.joda.time.DateTime
import org.pmp.budgeto.domain.PersistedActor
import org.scalatest.{BeforeAndAfter, FunSuite, GivenWhenThen}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

package object budgeto {

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
      expectedEventActor = system.actorOf(Props(new ExpectedEventActor(actorId("ExpectedEvent"), eventLog)))
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

  /**
   * list of commands
   */
  case class ListEvents()

  /**
   * replies on command
   */
  case class ListEventsSuccess(events: List[Any])

  /**
   * actor to log all sended events
   * @param id
   * @param eventLog
   */
  class ExpectedEventActor(override val id: String, override val eventLog: ActorRef) extends PersistedActor {

    var events: List[Any] = List.empty

    override val onCommand: Receive = {
      case ListEvents() => sender() ! ListEventsSuccess(events)
    }

    override val onEvent: Receive = {
      case e => events = events :+ e
    }
  }


}
