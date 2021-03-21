package demo.automation.challenge.utilities.enums;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static java.lang.String.format;

public class Calculations {

    public static long calculatePercentage(String amount, String total) {
        REPORTER.INFO(format("Calculating percentage for %s from a total of %s", amount, total));
        try {
            double aDouble = Double.parseDouble(amount);
            double tDouble = Double.parseDouble(total);
            return Math.round((aDouble*100)/tDouble);
        } catch (Exception e) {
            System.out.println("Numbers provided are not valid");
        }
        return 0;
    }
}
