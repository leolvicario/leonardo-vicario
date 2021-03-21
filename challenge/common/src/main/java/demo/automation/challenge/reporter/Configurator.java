package demo.automation.challenge.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import demo.automation.challenge.interfaces.Logging;
import lombok.Builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.aventstack.extentreports.reporter.configuration.Protocol.HTTPS;
import static com.aventstack.extentreports.reporter.configuration.Theme.DARK;
import static java.lang.String.format;

@Builder
public class Configurator implements Logging {
    private static final String DIRECTORY_REPORTS = "reports";
    private final static String NAME = "Challenge Application Report";

    private void configureHtmlReport(ExtentHtmlReporter htmlReporter, String name) {

        htmlReporter.config().enableTimeline(true);
        htmlReporter.config().setDocumentTitle(NAME);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setProtocol(HTTPS);
        htmlReporter.config().setReportName(name);
        htmlReporter.config().setTheme(DARK);
        htmlReporter.config().setTimeStampFormat("dd/MM/yyyy h:mm:ss");

        htmlReporter.start();
    }

    private String createDirectory(String name) {
        try {
            Files.createDirectories(Paths.get(DIRECTORY_REPORTS));
        } catch (IOException e) {
            getLogger().error("Unable to create Report's directory");
        }

        return format("%s/%s.html", DIRECTORY_REPORTS, name);
    }

    public ExtentReports createInstance(String name) {

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(createDirectory(name));
        configureHtmlReport(htmlReporter, name);

        ExtentReports report = new ExtentReports();
        report.attachReporter(htmlReporter);

        return report;
    }
}
