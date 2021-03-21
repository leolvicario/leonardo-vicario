package demo.automation.challenge.test.config.models;

import lombok.Data;

@Data
public class Settings {

    private String host;
    private String port;
    private String emulatorName;
    private Sut sut;
}
