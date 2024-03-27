package au.com.qantas.loyalty.lsl.candidatetask.member.model;

import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

  @JsonCreator
  public Member(
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @NotNull
  @JsonProperty(access = Access.READ_ONLY, index = 1)
  private Long memberId;

  @NotNull
  @JsonProperty(access = Access.READ_ONLY, index = 2)
  private AccountStatus accountStatus;

  @NotBlank
  @Size(max = 50)
  @JsonProperty(index = 3)
  private String firstName;

  @NotBlank
  @Size(max = 50)
  @JsonProperty(index = 4)
  private String lastName;

  @NotNull
  @JsonProperty(access = Access.READ_ONLY, index = 5)
  private LocalDate memberSince;

  @JsonProperty(access = Access.READ_ONLY, index = 6)
  private Set<Program> enrolledPrograms;

  @JsonProperty(index = 7)
  private OfferCategory offerCategoryPreference;

  @JsonProperty(access = Access.READ_ONLY, index = 8)
  private List<Offer> offers;

  @Data
  @Builder
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Offer {
    @NotNull
    @JsonProperty(access = Access.READ_ONLY, index = 1)
    private Long id;

    @Size(max = 20)
    @JsonProperty(access = Access.READ_ONLY, index = 2)
    private String name;

    @Size(max = 200)
    @JsonProperty(access = Access.READ_ONLY, index = 3)
    private String description;

  }
}
