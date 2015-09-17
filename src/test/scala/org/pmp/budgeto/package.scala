package org.pmp

import akka.actor.{ActorRef, ActorSystem}
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.joda.time.DateTime
import org.scalatest.{GivenWhenThen, BeforeAndAfter, FunSuite}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

package object budgeto {

  trait EventuateContext extends FunSuite with BeforeAndAfter with GivenWhenThen {
    implicit var system:ActorSystem = null
    implicit var timeout:Timeout = null
    var eventLog:ActorRef = null
    var logId:String = null

    def setup = {}

    before {
      system = ActorSystem("budgetoTest")
      timeout = Timeout(10.seconds)
      logId = DateTime.now().getMillis.toString
      eventLog = system.actorOf(LeveldbEventLog.props(logId = "budgetoTest" + logId, prefix = "test"))
      setup
    }

    after {
      system.terminate()
    }

    def actorId(name: String): String = name + logId

    def waitFor(future:Future[Any]) : Any = Await.result(future, Duration.Inf)

  }


}
