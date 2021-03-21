package demo.automation.challenge.models.ATO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class PetAto {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("category")
    public CategoryAto category;

    @JsonProperty("photoUrls")
    public List<String> photoUrls;

    @JsonProperty("tags")
    public List<TagAto> tags;

    @JsonProperty("status")
    public String status;

}
