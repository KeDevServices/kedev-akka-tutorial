package eu.kedev.training.akka.j1;

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
    }

    //State
    private int reqCount = 0;

    @Override
    public void onReceive(final Object message) throws Exception {
        if (message instanceof Count) {
            reqCount++;
            log.info("Someone says I should count. " + reportReqCount());
            getSender().tell("Nothing found to count", getSelf());
        }
    }

    private String reportReqCount() {
        return "I am asked " + reqCount + " time" + ((reqCount > 1) ? "s" : "") + ".";
    }
}

// from Scala:
//    def receive = {
//        case Count =>
//        reqCount = reqCount + 1
//            log.info("Someone says I should count. " + reportReqCount)
//        sender() ! "Nothing found to count"
//    }
//
//    private def reportReqCount =
//        "I am asked " + reqCount + " time" + (if (reqCount > 1) "s" else "") + "."


