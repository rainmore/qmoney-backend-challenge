package au.com.qantas.loyalty.lsl.candidatetask.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COUNTRY")
@SequenceGenerator(name="COUNTRY_ID_SEQUENCE", allocationSize = 100)
public class CountryEntity {

  @Id
  @GeneratedValue(generator = "COUNTRY_ID_SEQUENCE")
  @Column(name = "COUNTRY_ID", updatable = false, nullable = false)
  @NotNull
  private Long countryId;

  @NotBlank
  @Column(name = "COUNTRY_CODE_1", nullable = false)
  private String countryCode1;

  @NotBlank
  @Column(name = "COUNTRY_CODE_2", nullable = false)
  private String countryCode2;

  @NotBlank
  @Column(name = "COUNTRY_NAME", nullable = false)
  private String countryName;

}
