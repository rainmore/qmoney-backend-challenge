package au.com.qantas.loyalty.lsl.candidatetask.offers.model;

import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

  @NotNull
  @JsonProperty(access = Access.READ_ONLY, index = 1)
  private Long id;

  @Size(max = 20)
  @JsonProperty(index = 2)
  private String name;

  @Size(max = 20)
  @JsonProperty(index = 3)
  // TODO to confirm the property's display order as it was not added registered in `@JsonPropertyOrder`
  private OfferCategory category;

  @Size(max = 200)
  @JsonProperty(index = 4)
  private String description;
}
