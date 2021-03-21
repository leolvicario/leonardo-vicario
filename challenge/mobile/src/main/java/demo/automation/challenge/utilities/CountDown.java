package demo.automation.challenge.utilities;

import demo.automation.challenge.reporter.Reporter;
import lombok.SneakyThrows;

import java.util.Timer;
import java.util.TimerTask;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static java.lang.String.format;
import static java.lang.Thread.sleep;

public class CountDown {

    private static Timer timer;

    @SneakyThrows
    public static void launchCountdown(int seconds) {

        timer = new Timer();
        sleep(seconds*1000L); // Adding to stop the test thread while timer reaches 0
        timer.schedule(new TimerLogger(seconds), 0, seconds*1000L);
    }

    private static class TimerLogger extends TimerTask {

        int seconds;

        public TimerLogger(int secs) {
            seconds = secs;
        }

        @Override
        public void run() {
            REPORTER.INFO(format("Letting %d seconds pass", seconds));
        }
    }
}
