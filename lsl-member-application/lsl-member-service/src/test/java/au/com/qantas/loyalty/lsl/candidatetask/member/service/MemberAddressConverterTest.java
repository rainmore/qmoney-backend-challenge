package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.AddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressPK;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.AddressCategory;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Country;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MemberAddressConverterTest {

  private MemberAddressConverter memberAddressConverter;

  @BeforeEach
  void setup() {
    memberAddressConverter = new MemberAddressConverter();
  }

  @Test
  void testConvertFromEntity_withAllPropertiesSet() {
    MemberAddressEntity entity = buildEntity();
    Member.Address dto = memberAddressConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getCategory()).isEqualTo(AddressCategory.RESIDENTIAL);
    assertThat(dto.getAddress1()).isEqualTo("Qantas");
    assertThat(dto.getAddress2()).isEqualTo("10 Bourke road");
    assertThat(dto.getCity()).isEqualTo("Mascot");
    assertThat(dto.getPostcode()).isEqualTo("2020");
    assertThat(dto.getState()).isEqualTo("NSW");
    assertThat(dto.getCountry()).isEqualTo("Australia");
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    MemberAddressEntity entity = MemberAddressEntity.builder().build();
    Member.Address dto = memberAddressConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNull();
    assertThat(dto.getCategory()).isNull();
    assertThat(dto.getAddress1()).isNull();
    assertThat(dto.getAddress2()).isNull();
    assertThat(dto.getCity()).isNull();
    assertThat(dto.getPostcode()).isNull();
    assertThat(dto.getState()).isNull();
    assertThat(dto.getCountry()).isNull();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Member.Address dto = buildDto();
    MemberAddressEntity entity = memberAddressConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getId().getCategory()).isEqualTo(AddressCategory.RESIDENTIAL);
    assertThat(entity.getId().getAddress()).isNotNull();
    assertThat(entity.getId().getAddress().getAddressId()).isEqualTo(1L);
    assertThat(entity.getId().getAddress().getAddress1()).isEqualTo("Qantas");
    assertThat(entity.getId().getAddress().getAddress2()).isEqualTo("10 Bourke road");
    assertThat(entity.getId().getAddress().getCity()).isEqualTo("Mascot");
    assertThat(entity.getId().getAddress().getPostcode()).isEqualTo("2020");
    assertThat(entity.getId().getAddress().getState()).isEqualTo("NSW");
    assertThat(entity.getId().getAddress().getCountry().getCountryId()).isNull();
    assertThat(entity.getId().getAddress().getCountry().getCountryName()).isEqualTo("Australia");
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Member.Address dto = Member.Address.builder().build();
    MemberAddressEntity entity = memberAddressConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getId().getCategory()).isEqualTo(AddressCategory.RESIDENTIAL);
    assertThat(entity.getId().getAddress()).isNotNull();
    assertThat(entity.getId().getAddress().getAddressId()).isNull();
    assertThat(entity.getId().getAddress().getAddress1()).isNull();
    assertThat(entity.getId().getAddress().getAddress2()).isNull();
    assertThat(entity.getId().getAddress().getCity()).isNull();
    assertThat(entity.getId().getAddress().getPostcode()).isNull();
    assertThat(entity.getId().getAddress().getState()).isNull();
    assertThat(entity.getId().getAddress().getCountry()).isNull();
  }

  @Test
  void testCopyDtoToEntity_withAllPropertiesSet() {
    Member.Address dto = buildDto();
    MemberAddressEntity entity = new MemberAddressEntity();
    memberAddressConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getId().getCategory()).isEqualTo(AddressCategory.RESIDENTIAL);
    assertThat(entity.getId().getAddress()).isNotNull();
    assertThat(entity.getId().getAddress().getAddressId()).isEqualTo(1L);
    assertThat(entity.getId().getAddress().getAddress1()).isEqualTo("Qantas");
    assertThat(entity.getId().getAddress().getAddress2()).isEqualTo("10 Bourke road");
    assertThat(entity.getId().getAddress().getCity()).isEqualTo("Mascot");
    assertThat(entity.getId().getAddress().getPostcode()).isEqualTo("2020");
    assertThat(entity.getId().getAddress().getState()).isEqualTo("NSW");
    assertThat(entity.getId().getAddress().getCountry().getCountryId()).isNull();
    assertThat(entity.getId().getAddress().getCountry().getCountryName()).isEqualTo("Australia");
  }

  @Test
  void testCopyDtoToEntity_withEmtpyDto() {
    Member.Address dto = Member.Address.builder().build();
    MemberAddressEntity entity = buildEntity();
    memberAddressConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getId().getCategory()).isNull();
    assertThat(entity.getId().getAddress()).isNotNull();
    assertThat(entity.getId().getAddress().getAddressId()).isNull();
    assertThat(entity.getId().getAddress().getAddress1()).isNull();
    assertThat(entity.getId().getAddress().getAddress2()).isNull();
    assertThat(entity.getId().getAddress().getCity()).isNull();
    assertThat(entity.getId().getAddress().getPostcode()).isNull();
    assertThat(entity.getId().getAddress().getState()).isNull();
    assertThat(entity.getId().getAddress().getCountry()).isNull();
  }

  private MemberAddressEntity buildEntity() {
    return MemberAddressEntity.builder()
      .id(MemberAddressPK.builder()
        .member(buildMemberEntity())
        .address(buildAddressEntity())
        .category(AddressCategory.RESIDENTIAL)
        .build())
      .build();
  }

  private AddressEntity buildAddressEntity() {
    return AddressEntity.builder()
      .addressId(1L)
      .address1("Qantas")
      .address2("10 Bourke road")
      .city("Mascot")
      .postcode("2020")
      .state("NSW")
      .country(buildCountryEntity())
      .build();
  }

  private CountryEntity buildCountryEntity() {
    return CountryEntity.builder()
      .countryId(36L)
      .countryCode1("AU")
      .countryCode2("AUS")
      .countryName("Australia")
      .build();
  }

  private Member.Address buildDto() {
    return Member.Address.builder()
      .id(1L)
      .address1("Qantas")
      .address2("10 Bourke road")
      .city("Mascot")
      .postcode("2020")
      .state("NSW")
      .country(buildCountryDto().getName())
      .category(AddressCategory.RESIDENTIAL)
      .build();
  }

  private Country buildCountryDto() {
    return Country.builder()
      .countryId(36L)
      .countryCode("AU")
      .countryCode2("AUS")
      .name("Australia")
      .build();
  }

  private MemberEntity buildMemberEntity() {
    Set<ProgramEntity> programEntities = new HashSet<>();
    programEntities.add(
      ProgramEntity.builder()
        .programId("FF")
        .programName("Frequent Flyer")
        .programDescription("Frequent Flyer program")
        .build()
    );

    return MemberEntity.builder()
      .memberId(112233L)
      .accountStatus(AccountStatus.ACTIVE)
      .givenName("Fred")
      .surname("Flintstone")
      .enrolledSince(LocalDate.of(2017, 07, 26))
      .offerCategoryPreference(OfferCategory.NATURE)
      .enrolledPrograms(programEntities)
      .build();
  }

}
