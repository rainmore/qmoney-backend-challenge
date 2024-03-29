package au.com.qantas.loyalty.lsl.candidatetask.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
@SequenceGenerator(name="ADDRESS_ID_SEQUENCE", allocationSize = 100)
public class AddressEntity {

  @Id
  @GeneratedValue(generator = "ADDRESS_ID_SEQUENCE")
  @Column(name = "ADDRESS_ID", updatable = false, nullable = false)
  @NotNull
  private Long addressId;

  @NotBlank
  @Column(name = "ADDRESS_1", nullable = false)
  private String address1;

  @NotBlank
  @Column(name = "ADDRESS_2", nullable = false)
  private String address2;

  @NotBlank
  @Column(name = "CITY", nullable = false)
  private String city;

  @NotBlank
  @Column(name = "POSTCODE", nullable = false)
  private String postcode;


  @NotBlank
  @Column(name = "STATE", nullable = false)
  private String state;


  @NotNull
  @ManyToOne
  @JoinColumn(name = "COUNTRY_ID", updatable = false, nullable = false)
  private CountryEntity country;
}
