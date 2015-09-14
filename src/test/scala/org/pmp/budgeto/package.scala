package org.pmp

import akka.actor.{ActorRef, ActorSystem}
import akka.util.Timeout
import com.rbmhtechnology.eventuate.log.leveldb.LeveldbEventLog
import org.joda.time.DateTime
import org.scalatest.{BeforeAndAfter, FunSuite}
import scala.concurrent.duration._

package object budgeto {

  class IdGeneratorFixed(var value: String) {
    def next(): String = value
  }

  trait EventuateContext extends FunSuite with BeforeAndAfter {
    implicit var system:ActorSystem = null
    implicit var timeout:Timeout = null
    var eventLog:ActorRef = null
    var logId:String = null

    before {
      system = ActorSystem("budgetoTest")
      timeout = Timeout(10.seconds)
      logId = DateTime.now().getMillis.toString
      eventLog = system.actorOf(LeveldbEventLog.props(logId = "budgetoTest" + logId, prefix = "test"))
    }

    after {
      system.terminate()
    }

    def actorId(name: String): String = name + logId

  }


}
