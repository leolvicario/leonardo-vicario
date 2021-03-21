package demo.automation.challenge.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDto {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("petId")
    public Integer petId;

    @JsonProperty("quantity")
    public Integer quantity;

    @JsonProperty("shipDate")
    public String shipDate;

    @JsonProperty("status")
    public String status;

    @JsonProperty("complete")
    public Boolean complete;
}
