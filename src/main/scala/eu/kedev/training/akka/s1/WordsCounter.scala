package eu.kedev.training.akka.s1

import akka.actor.{Props, Actor, ActorLogging}
import WordsCounter.Count

object WordsCounter {
  val props = Props[WordsCounter]
  case object Count
}

class WordsCounter extends Actor with ActorLogging {
	var reqCount: Int = 0

  def receive = {
  	case Count =>
			reqCount = reqCount + 1
	    log.info("Someone says I should count. " + reportReqCount)
			sender() ! "Nothing found to count"

  }

  private def reportReqCount =
    "I am asked " + reqCount + " time" + (if (reqCount > 1) "s" else "") + "."
}