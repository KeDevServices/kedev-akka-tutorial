package eu.kedev.training.akka.j1;

import static org.junit.Assert.assertEquals;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Joachim Klein, joachim.klein@secure.avono.de
 * @since 17.07.15
 */
public class WordsCounterTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void tearDown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testIfNothingFoundToCountIsSendOnCountMsg() {
        new JavaTestKit(system) {{
            final ActorRef wordsCounter = system.actorOf(WordsCounter.props());
            wordsCounter.tell(new WordsCounter.Count(), getRef());

            final String countResult = new ExpectMsg<String>("the String: 'Nothing found to count'") {
                // do not put code outside this method, will run afterwards
                protected String match(Object in) {
                    if (in instanceof String) {
                        return in.toString();
                    }
                    throw noMatch();
                }
            }.get(); // this extracts the received message

            assertEquals("Nothing found to count", countResult);
        }};
    }
}
