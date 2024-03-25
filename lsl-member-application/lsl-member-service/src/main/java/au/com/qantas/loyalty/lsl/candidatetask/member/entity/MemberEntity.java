package au.com.qantas.loyalty.lsl.candidatetask.member.entity;

import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
@SequenceGenerator(name="MEMBER_ID_SEQUENCE", allocationSize = 100)
public class MemberEntity implements Serializable {

  @Id
  @GeneratedValue(generator = "MEMBER_ID_SEQUENCE")
  @Column(name = "MEMBER_ID", updatable = false, nullable = false)
  @NotNull
  private Long memberId;

  @NotNull
  @Column(name = "ACCOUNT_STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus;

  @NotNull
  @Column(name = "GIVEN_NAME", nullable = false)
  private String givenName;

  @NotNull
  @Column(name = "SURNAME", nullable = false)
  private String surname;

  @NotNull
  @Column(name = "ENROLLED_SINCE", nullable = false)
  private LocalDate enrolledSince;

  @NotNull
  @Column(name = "PREFERENCE", nullable = false)
  @Enumerated(EnumType.STRING)
  private OfferCategory offerCategoryPreference;
}
