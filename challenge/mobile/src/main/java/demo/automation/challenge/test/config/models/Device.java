package demo.automation.challenge.test.config.models;

import lombok.Data;

import java.util.Map;

@Data
public class Device {

    private String name;
    private Map<String, Object> capabilities;
}
