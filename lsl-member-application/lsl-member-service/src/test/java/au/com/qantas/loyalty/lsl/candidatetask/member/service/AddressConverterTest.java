package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.AddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Address;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressConverterTest {

  private CountryConverter countryConverter;
  private AddressConverter addressConverter;

  @BeforeEach
  void setup() {
    countryConverter = new CountryConverter();
    addressConverter = new AddressConverter(countryConverter);
  }

  @Test
  void testConvertFromEntity_withAllPropertiesSet() {
    AddressEntity entity = buildEntity();
    Address dto = addressConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getAddressId()).isEqualTo(1L);
    assertThat(dto.getAddress1()).isEqualTo("Qantas");
    assertThat(dto.getAddress2()).isEqualTo("10 Bourke road");
    assertThat(dto.getCity()).isEqualTo("Mascot");
    assertThat(dto.getPostcode()).isEqualTo("2020");
    assertThat(dto.getState()).isEqualTo("NSW");
    assertThat(dto.getCountry().getCountryId()).isEqualTo(36L);
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    AddressEntity entity = AddressEntity.builder().build();
    Address dto = addressConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getAddressId()).isNull();
    assertThat(dto.getAddress1()).isNull();
    assertThat(dto.getAddress2()).isNull();
    assertThat(dto.getCity()).isNull();
    assertThat(dto.getPostcode()).isNull();
    assertThat(dto.getState()).isNull();
    assertThat(dto.getCountry()).isNull();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Address dto = buildDto();
    AddressEntity entity = addressConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getAddressId()).isEqualTo(1L);
    assertThat(entity.getAddress1()).isEqualTo("Qantas");
    assertThat(entity.getAddress2()).isEqualTo("10 Bourke road");
    assertThat(entity.getCity()).isEqualTo("Mascot");
    assertThat(entity.getPostcode()).isEqualTo("2020");
    assertThat(entity.getState()).isEqualTo("NSW");
    assertThat(entity.getCountry().getCountryId()).isEqualTo(36L);
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Address dto = Address.builder().build();
    AddressEntity entity = addressConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getAddressId()).isNull();
    assertThat(entity.getAddress1()).isNull();
    assertThat(entity.getAddress2()).isNull();
    assertThat(entity.getCity()).isNull();
    assertThat(entity.getPostcode()).isNull();
    assertThat(entity.getState()).isNull();
    assertThat(entity.getCountry()).isNull();
  }

  @Test
  void testCopyDtoToEntity_withAllPropertiesSet() {
    Address dto = buildDto();
    AddressEntity entity = AddressEntity.builder().build();
    addressConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getAddressId()).isEqualTo(1L);
    assertThat(entity.getAddress1()).isEqualTo("Qantas");
    assertThat(entity.getAddress2()).isEqualTo("10 Bourke road");
    assertThat(entity.getCity()).isEqualTo("Mascot");
    assertThat(entity.getPostcode()).isEqualTo("2020");
    assertThat(entity.getState()).isEqualTo("NSW");
    assertThat(entity.getCountry().getCountryId()).isEqualTo(36L);
  }

  @Test
  void testCopyDtoToEntity_withEmtpyDto() {
    Address dto = Address.builder().build();
    AddressEntity entity = buildEntity();
    addressConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getAddressId()).isNull();
    assertThat(entity.getAddress1()).isNull();
    assertThat(entity.getAddress2()).isNull();
    assertThat(entity.getCity()).isNull();
    assertThat(entity.getPostcode()).isNull();
    assertThat(entity.getState()).isNull();
    assertThat(entity.getCountry()).isNull();
  }

  private AddressEntity buildEntity() {
    //(1, 'Qantas', '10 Bourke road', 'Mascot', '2020', 'NSW', '36'),
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

  private Address buildDto() {
    return Address.builder()
      .addressId(1L)
      .address1("Qantas")
      .address2("10 Bourke road")
      .city("Mascot")
      .postcode("2020")
      .state("NSW")
      .country(buildCountryDto())
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
}
