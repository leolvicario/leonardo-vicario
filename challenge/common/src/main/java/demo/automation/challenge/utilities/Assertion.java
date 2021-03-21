package demo.automation.challenge.utilities;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import static demo.automation.challenge.reporter.Reporter.REPORTER;

public class Assertion {

    // Created to show passing assertions in report
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(reason, actual, matcher);
        REPORTER.PASS(reason);
    }

}
