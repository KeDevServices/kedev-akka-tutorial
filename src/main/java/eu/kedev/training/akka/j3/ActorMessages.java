package eu.kedev.training.akka.j3;

/**
 * @author Joachim Klein, joachim.klein@secure.avono.de
 * @since 21.10.15
 */


import java.util.Date;

public class ActorMessages {
    public static class Start {
    }

    public static class TimedStart {
        private final Date date;

        public TimedStart(Date date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "TimedStart{" + "date=" + date + '}';
        }
    }

    public static class Stop {
    }
}
