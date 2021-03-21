package demo.automation.challenge.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InventoryDto {

    @JsonProperty("approved")
    public Integer approved;

    @JsonProperty("placed")
    public Integer placed;

    @JsonProperty("delivered")
    public Integer delivered;
}
