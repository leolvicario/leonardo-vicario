package demo.automation.challenge.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PetDto {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("category")
    public CategoryDto category;

    @JsonProperty("photoUrls")
    public List<String> photoUrls;

    @JsonProperty("tags")
    public List<TagDto> tags;

    @JsonProperty("status")
    public String status;

}
