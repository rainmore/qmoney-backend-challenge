package au.com.qantas.loyalty.lsl.candidatetask.member.model;

import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonPropertyOrder({
    "memberId",
    "accountStatus",
    "firstName",
    "lastName",
    "memberSince",
    "enrolledPrograms",
    "preference",
    "offers"
})
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
  @JsonProperty(access = Access.READ_ONLY)
  private Long memberId;

  @NotNull
  @JsonProperty(access = Access.READ_ONLY)
  private AccountStatus accountStatus;

  @NotBlank
  @Size(max = 50)
  private String firstName;

  @NotBlank
  @Size(max = 50)
  private String lastName;

  @NotNull
  @JsonProperty(access = Access.READ_ONLY)
  private LocalDate memberSince;

  @JsonProperty(access = Access.READ_ONLY)
  private Set<Program> enrolledPrograms;

  private OfferCategory offerCategoryPreference;

  @JsonProperty(access = Access.READ_ONLY)
  private List<Offer> offers;

  @Data
  @Builder
  @AllArgsConstructor
  @JsonPropertyOrder({
      "id",
      "name",
      "description"
  })
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Offer {
    @NotNull
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @Size(max = 20)
    @JsonProperty(access = Access.READ_ONLY)
    private String name;

    @Size(max = 200)
    @JsonProperty(access = Access.READ_ONLY)
    private String description;

  }
}
