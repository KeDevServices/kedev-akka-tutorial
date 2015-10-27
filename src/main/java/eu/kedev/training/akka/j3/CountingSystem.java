package eu.kedev.training.akka.j3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import java.util.Date;

public class CountingSystem {

    private ActorSystem system;

    public static void main(String[] args) {
        final CountingSystem countingSystem = new CountingSystem();
        countingSystem.system = ActorSystem.create("countingSystem");
        ActorRef supervisor = countingSystem.system.actorOf(CountingSupervisor.props("/tmp"), "supervisor");
        ActorRef watcher = countingSystem.system.actorOf(CountingSystem.props(supervisor, countingSystem), "watcher");
        supervisor.tell(new ActorMessages.Start(), ActorRef.noSender());
        supervisor.tell(new ActorMessages.Start(), ActorRef.noSender());
        supervisor.tell(new ActorMessages.TimedStart(new Date()), ActorRef.noSender());
        supervisor.tell(new ActorMessages.Stop(), ActorRef.noSender());
    }

    public static Props props(final ActorRef child, final CountingSystem countingSystem) {
        return Props.create(new Creator<WatchActor>() {
            private static final long serialVersionUID = 1L;

            @Override
            public WatchActor create() throws Exception {
                return countingSystem.new WatchActor(child);
            }
        });
    }

    private class WatchActor extends UntypedActor {
        LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        private ActorRef child;

        public WatchActor(ActorRef child) {
            this.child = child;
            this.getContext().watch(child); // <-- the only call needed for registration
        }

        ActorRef lastSender = getContext().system().deadLetters();

        @Override
        public void onReceive(Object message) {
            if (message instanceof Terminated) {
                final Terminated t = (Terminated) message;
                if (t.getActor() == child) {
                    lastSender.tell("finished", getSelf());
                    log.info("Child stopped.");
                    CountingSystem.this.shutdown();
                }
            } else {
                unhandled(message);
            }
        }
    }

    public void shutdown() {
        system.shutdown();
    }
}
