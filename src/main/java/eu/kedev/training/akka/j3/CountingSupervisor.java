package eu.kedev.training.akka.j3;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

public class CountingSupervisor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final String directoryPath;
    private boolean started = false;

    public static Props props(final String directoryPath) {
        return Props.create(new Creator<CountingSupervisor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public CountingSupervisor create() throws Exception {
                return new CountingSupervisor(directoryPath);
            }
        });
    }

    public CountingSupervisor(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ActorMessages.Stop) {
            log.info("I stop now.");
            getContext().stop(self());
        } else if (message instanceof ActorMessages.Start) {
            if (isStarted()) {
                log.error("I am already started.");
            } else {
                ActorRef counter = getContext().actorOf(WordsCounter.props(), "counter");
                counter.tell(new WordsCounter.Count("Kenn ich net"), this.self());
                setStarted(true);
                log.info("I am starting now.");
            }
        } else if (message instanceof Long) {
            log.info("Words: " + message);
        } else {
            log.warning("WHAT: " + message);
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
