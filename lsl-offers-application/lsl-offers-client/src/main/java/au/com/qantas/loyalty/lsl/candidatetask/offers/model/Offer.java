package au.com.qantas.loyalty.lsl.candidatetask.offers.model;

import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "id",
    "name",
    "description"
})
public class Offer {

  @NotNull
  @JsonProperty(access = Access.READ_ONLY)
  private Long id;

  @Size(max = 20)
  private String name;

  @Size(max = 20)
  private OfferCategory category;

  @Size(max = 200)
  private String description;
}
