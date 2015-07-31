package eu.kedev.training.akka.j2;

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

    /**
     * In Scala:
     * <pre>
     * "send back the number of words in a given String" in {
     *   val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *   wordsCounter ! s2.WordsCounter.Count("This is an simple text with 8 words")
     *   expectMsg(8)
     * }
     * </pre>
     */
    @Test
    public void mustSendBackTheNumberOfWordsInAGivenString() {
        assertLongMsgWithValueFor(8l, "This is an simple text with 8 words");
    }

    /**
     * In Scala:
     * <pre>
     * "send back the count zero if the string is empty" in {
     *   val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *   wordsCounter ! s2.WordsCounter.Count("")
     *   expectMsg(0)
     * }
     * </pre>
     */
    @Test
    public void mustSendBackTheCountZeroIfTheStringIsEmpty() {
        assertLongMsgWithValueFor(0l, "");
    }

    /**
     * In Scala:
     * <pre>
     * "send back the count zero if the string is nearly empty (something, but no words)" in {
     *    val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *    wordsCounter ! s2.WordsCounter.Count(" \n\t   \t ")
     *    expectMsg(0)
     * }
     * </pre>
     */
    @Test
    public void mustSendBackTheCountZeroIfTheStringContainsSomethingButNoWords() {
        assertLongMsgWithValueFor(0l, " \n\t   \t ");
    }

    /**
     * In Scala:
     * <pre>
     * "send back 4 for 'Something \\n\\t could be wrong'" in {
     *   val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *   wordsCounter ! s2.WordsCounter.Count("Something \n\t could be wrong")
     *   expectMsg(4)
     * }
     * </pre>
     */
    @Test
    public void mustSendBack4ForAStringWith4WordsIncludingWhiteSpaceChars() {
        assertLongMsgWithValueFor(4l, "Something \n\t could be wrong\"");
    }

    /**
     * In Scala:
     * <pre>
     * "send back 4 for 'Something - could be wrong ? ! .'" in {
     *   val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *   wordsCounter ! s2.WordsCounter.Count("Something - could be wrong ? ! .")
     *  expectMsg(4)
     * }
     * </pre>
     */
    @Test
    public void mustSendBack4ForAStringWith4WordsIncludingPunctuationMarks() {
        assertLongMsgWithValueFor(4l, "Something - could be wrong ? ! .");
    }

    /**
     * In Scala:
     * <pre>
     * "send back 'Nothing found to count' if companion object is send" in {
     *   val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *   wordsCounter ! s2.WordsCounter.Count
     *   expectMsg("Nothing found to count")
     * }
     * </pre>
     */
//    In Java?? - Not possible
//    @Test
//    public void mustSendBackNothingFoundToCountIfCompanionObjectIsSend() {
//        ??
//    }

    /**
     * In Scala:
     * <pre>
     * "send back 'Nothing found to count' if null is send" in {
     *   val wordsCounter = system.actorOf(s2.WordsCounter.props)
     *   wordsCounter ! s2.WordsCounter.Count(null)
     *   expectMsg("Nothing found to count")
     * }
     * </pre>
     */
    @Test
    public void mustSendBackNothingFoundToCountIfNullIsSend() {
        new JavaTestKit(system) {{
            final ActorRef wordsCounter = system.actorOf(WordsCounter.props());
            wordsCounter.tell(new WordsCounter.Count(null), getRef());

            final String countResult = new ExpectMsg<String>("Should answer: 'Nothing found to count'") {
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


    private void assertLongMsgWithValueFor(long expectedValue, String textToCountWordsOn) {
        new JavaTestKit(system) {{
            final ActorRef wordsCounter = system.actorOf(WordsCounter.props());
            wordsCounter.tell(new WordsCounter.Count(textToCountWordsOn), getRef());

            final Long countResult = new ExpectMsg<Long>("Should return the Integer value: " + expectedValue) {
                // do not put code outside this method, will run afterwards
                protected Long match(Object in) {
                    if (in instanceof Long) {
                        return (Long) in;
                    }
                    throw noMatch();
                }
            }.get(); // this extracts the received message

            assertEquals(Long.valueOf(expectedValue), countResult);
        }};
    }
}
