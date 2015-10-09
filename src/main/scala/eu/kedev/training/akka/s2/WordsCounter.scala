package eu.kedev.training.akka.s2

import akka.actor.{SupervisorStrategy, Actor, ActorLogging, Props}
import eu.kedev.training.akka.s2.WordsCounter.Count

object WordsCounter {
  val props = Props[WordsCounter]
  case class Count(inStr: String)
}

class WordsCounter extends Actor with ActorLogging {
	var reqCount: Int = 0

  def receive = {
    reqCount = reqCount + 1
    log.info("Someone says I should count. " + reportReqCount)

    //SupervisorStrategy.defaultStrategy

    {
      case Count(null) =>
        sender() ! "Nothing found to count"
      case Count(inStr) =>
        sender() ! countWords(inStr)
      case others =>
        sender() ! "Nothing found to count"
    }
  }

  private def reportReqCount =
    "I am asked " + reqCount + " time" + (if (reqCount > 1) "s" else "") + "."

  private def countWords(inStr: String) = {
    inStr.split("[^\\w]+").count(!_.isEmpty)
  }
}