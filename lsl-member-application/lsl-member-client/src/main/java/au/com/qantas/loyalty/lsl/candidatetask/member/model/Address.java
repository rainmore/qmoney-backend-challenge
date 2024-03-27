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
public class Address {

  @NotNull
  @Size(max = 8)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, index = 1)
  private Long addressId;

  @NotBlank
  @Size(max = 200)
  @JsonProperty(index = 2)
  private String address1;

  @Size(max = 200)
  @JsonProperty(index = 3)
  private String address2;

  @NotBlank
  @Size(max = 50)
  @JsonProperty(index = 4)
  private String city;

  @NotBlank
  @Size(max = 50)
  @JsonProperty(index = 5)
  private String postcode;

  @NotBlank
  @Size(max = 50)
  @JsonProperty(index = 6)
  private String state;

  @NotNull
  @Size(max = 8)
  @JsonProperty(index = 7)
  private Country country;
}
