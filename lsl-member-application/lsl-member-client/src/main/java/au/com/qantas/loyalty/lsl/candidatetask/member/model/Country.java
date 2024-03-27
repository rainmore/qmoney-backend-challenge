package au.com.qantas.loyalty.lsl.candidatetask.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country {

  @NotNull
  @Size(max = 8)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, index = 1)
  private Long countryId;

  @NotBlank
  @Size(max = 2)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, index = 2)
  private String countryCode;

  @NotBlank
  @Size(max = 3)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, index = 3)
  private String countryCode2;

  @NotBlank
  @Size(max = 100)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, index = 4)
  private String name;
}
