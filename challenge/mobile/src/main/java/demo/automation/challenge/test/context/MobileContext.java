package demo.automation.challenge.test.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import demo.automation.challenge.interfaces.Logging;
import demo.automation.challenge.test.config.models.Device;
import demo.automation.challenge.test.config.models.Settings;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public enum MobileContext implements Logging {

    INSTANCE;

    private static final ThreadLocal<AppiumDriver> DRIVER = new InheritableThreadLocal<>();
    private static final ThreadLocal<AppiumDriverLocalService> SERVER = new InheritableThreadLocal<>();

    private static final String PATH_TO_SETTINGS = "src/test/resources/settings.yml";
    private final Settings settings;

    MobileContext() {
        getLogger().info("***** Initializing Mobile Configuration *****");
        settings = readFromSettingsFile();
    }

    @SneakyThrows
    public void init(Platform p) {
        createServer();
        getLogger().info("Setting up Appium hearing host");
        createDriver(getServer().getUrl(), createCapabilities(p));
    }

    private void createServer() {
        getLogger().info("Creating Appium server");
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingPort(Integer.parseInt(settings.getPort()));
        SERVER.set(AppiumDriverLocalService.buildService(serviceBuilder));

        getLogger().info("Starting Appium server");
        getServer().start();
    }

    private void createDriver(URL url, Capabilities c) {
        destroy();
        getLogger().info("Creating Appium Driver");
        AppiumDriver<WebElement> driver = "Android".equalsIgnoreCase(c.getPlatform().name()) ? new AndroidDriver<>(url, c) : new IOSDriver<>(url, c);
        DRIVER.set(driver);
    }

    public void destroy() {
        AppiumDriver driver = getDriver();

        if (driver != null) {
            getLogger().info("Destroying any existing Appium Driver Instance");
            String udid = driver.getCapabilities().getCapability("udid").toString();

            getDriver().quit();

            if (udid.contains("emulator")) {
                stopEmulator(udid);
            }
        }
        DRIVER.remove();


    }

    public void stopServer() {
        AppiumDriverLocalService server = getServer();

        if (server.isRunning()) {
            getLogger().info("Stopping Appium server");
            server.stop();
        }
        SERVER.remove();
    }

    private Settings readFromSettingsFile() {
        Settings settings = null;
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        File file = new File(PATH_TO_SETTINGS);
        getLogger().info(format("Reading from settings file %s", file.toPath().toString()));
        try {
            settings = om.readValue(file, Settings.class);
        } catch (IOException e) {
            getLogger().info(format("Unable to extract settings from file, error -> %s", e.getMessage()));
        }
        return settings;
    }

    private Device getDevice(Platform p) {
        for (Device d : settings.getSut().getDevices()) {
            if (d.getName().equals(p.name())) {
                getLogger().info(format("Device -> %s", d.getName()));
                if (d.getName().toLowerCase().contains("emulator")) {
                    triggerEmulator();
                }
                return d;
            }
        }
        return new Device();
    }

    private Capabilities createCapabilities(Platform p) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        getLogger().info("Setting Android capabilities");
        for (Map.Entry<String, Object> cap : getDevice(p).getCapabilities().entrySet()) {
            if ("app".equals(cap.getKey())) {
                getLogger().info(format("APK -> %s", cap.getValue()));
                desiredCapabilities.setCapability("app", new File(cap.getValue().toString()).getAbsolutePath());
            } else {
                desiredCapabilities.setCapability(cap.getKey(), cap.getValue());
            }
        }
        return desiredCapabilities;
    }

    @SneakyThrows
    private void triggerEmulator() {
        getLogger().info("Starting simulator");
        Runtime.getRuntime().exec(format("emulator @%s", settings.getEmulatorName())).waitFor(5, TimeUnit.SECONDS);
        getLogger().info("Simulator started");
    }

    @SneakyThrows
    private void stopEmulator(String udid) {
        getLogger().info("Stopping simulator");
        Runtime.getRuntime().exec(format("adb -s %s emu kill", udid)).waitFor(5, TimeUnit.SECONDS);
        getLogger().info("Simulator stopped");
    }

    public AppiumDriverLocalService getServer() {
        return SERVER.get();
    }

    public AppiumDriver getDriver() {
        return DRIVER.get();
    }

}
