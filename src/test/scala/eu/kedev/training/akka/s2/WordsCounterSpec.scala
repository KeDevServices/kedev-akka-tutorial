package eu.kedev.training.akka.s2

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import eu.kedev.training.akka.s2
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * @author Joachim Klein, joachim.klein@secure.avono.de
 * @since 14.07.15
 */
class WordsCounterSpec(_system: ActorSystem)
    extends TestKit(_system)
    with ImplicitSender
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("MySpec"))

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  "A WordsCounter Actor" must {
    "send back the number of words in a given String" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count("This is an simple text with 8 words")
      expectMsg(8)
    }
    "send back the count zero if the string is empty" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count("")
      expectMsg(0)
    }
    "send back the count zero if the string is nearly empty (something, but no words)" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count(" \n\t   \t ")
      expectMsg(0)
    }
    "send back 4 for 'Something \\n\\t could be wrong'" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count("Something \n\t could be wrong")
      expectMsg(4)
    }
    "send back 4 for 'Something - could be wrong'" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count("Something - could be wrong ? ! .")
      expectMsg(4)
    }
    "send back 'Nothing found to count' if companion object is send" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count
      expectMsg("Nothing found to count")
    }
    "send back 'Nothing found to count' if null is send" in {
      val wordsCounter = system.actorOf(s2.WordsCounter.props)
      wordsCounter ! s2.WordsCounter.Count(null)
      expectMsg("Nothing found to count")
    }

  }
}

