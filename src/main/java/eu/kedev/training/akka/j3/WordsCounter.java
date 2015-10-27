

package eu.kedev.training.akka.j3;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author Joachim Klein, joachim.klein@secure.avono.de
 * @since 17.07.15
 */
public class WordsCounter extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //Props
    public static Props props() {
        return Props.create(WordsCounter.class);
    }

    //Message Objects
    public static class Count {
        private final String inStr;

        public Count(final String inStr) {
            this.inStr = inStr;
        }

        public String getInStr() {
            return inStr;
        }
    }

    //State
    private int reqCount = 0;

    @Override
    public void onReceive(final Object message) throws Exception {
        reqCount++;
        log.info("Someone says I should count. " + reportReqCount());

        if (message instanceof Count) {
            Count countReq = (Count) message;
            if (countReq.getInStr() != null) {
                getSender().tell(countWords(countReq.getInStr()), getSelf());
            } else {
                getSender().tell("Nothing found to count", getSelf());
            }
        }
    }

    private String reportReqCount() {
        return "I am asked " + reqCount + " time" + ((reqCount > 1) ? "s" : "") + ".";
    }

    private long countWords(final String inStr) {
        long counter = 0;
        for (String word : inStr.split("[^\\w]")) {
            if (!word.isEmpty()) {
                counter++; //count
            }
        }
        return counter;
    }
}

// from Scala:
// class WordsCounter extends Actor with ActorLogging {
//     var reqCount: Int = 0
//
//     def receive = {
//         reqCount = reqCount  1
//         log.info("Someone says I should count. "  reportReqCount)
//
//         {
//           case Count(null) =>
//             sender() ! "Nothing found to count"
//           case Count(inStr) =>
//             sender() ! countWords(inStr)
//           case others =>
//             sender() ! "Nothing found to count"
//         }
//     }
//
//     private def reportReqCount =
//         "I am asked "  reqCount  " time"  (if (reqCount > 1) "s" else "")  "."
//
//     private def countWords(inStr: String) = {
//         inStr.split("[^\\w]").count(!_.isEmpty)
//     }
// }

