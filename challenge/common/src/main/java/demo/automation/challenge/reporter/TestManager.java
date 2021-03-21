package demo.automation.challenge.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import demo.automation.challenge.interfaces.Logging;
import lombok.Builder;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

@Builder
public class TestManager implements Logging {

    private static ThreadLocal<ExtentTest> TEST;
    private final ExtentReports report;

    public void createTest(Method method) {
        String name = extractName(method);
        TEST = new InheritableThreadLocal<>();
        TEST.set(report.createTest(name));
        getLogger().info(format("Starting %s test", name));
    }

    public void addDescription(Method method) {
        TEST.get().getModel().setDescription(extractDescription(method));
    }

    public void addGroups(Method method) {
        TEST.get().assignCategory(extractGroups(method));
    }

    public Optional<ExtentTest> getTest() {
        return ofNullable(TEST.get());
    }

    private String extractName(Method method) {
        Annotation annotation = stream(method.getDeclaredAnnotations()).filter(a -> a.annotationType().equals(Test.class)).findFirst().orElse(null);
        if (annotation != null && !((Test) annotation).testName().isEmpty()) {
            return ((Test) annotation).testName();
        }
        return method.getName();
    }

    private String extractDescription(Method method) {
        Optional<Annotation> annotation = stream(method.getDeclaredAnnotations()).filter(a -> a.annotationType().equals(Test.class)).findAny();
        return annotation.map(a -> ((Test) a).description()).orElse("");
    }

    private String[] extractGroups(Method method) {
        Optional<Annotation> annotation = stream(method.getDeclaredAnnotations()).filter(a -> a.annotationType().equals(Test.class)).findAny();
        return annotation.map(a -> ((Test) a).groups()).orElse(new String[]{});
    }

}
