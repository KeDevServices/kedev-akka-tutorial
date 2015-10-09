package eu.kedev.training.akka.s1

import akka.actor.ActorSystem
import akka.testkit.{ TestActors, TestKit, ImplicitSender }
import eu.kedev.training.akka.s1
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

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

  // Sagen, wo und wie man diese Traits findet

  def this() = this(ActorSystem("MySpec"))

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  // Wo findet man WordSpecLike

  "A WordsCounter Actor" must {
    "send back 'Nothing found to count'" in {
      val wordsCounter = system.actorOf(s1.WordsCounter.props)
      wordsCounter ! s1.WordsCounter.Count
      expectMsg("Nothing found to count")
    }
  }
}

