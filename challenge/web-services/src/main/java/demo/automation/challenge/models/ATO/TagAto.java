package demo.automation.challenge.models.ATO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class TagAto {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

}
