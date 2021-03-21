package demo.automation.challenge.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TagDto {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

}
