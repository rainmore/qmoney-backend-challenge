package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryConverterTest {

  private CountryConverter countryConverter;

  @BeforeEach
  void setup() {
    countryConverter = new CountryConverter();
  }

  @Test
  void testConvertFromEntity_withAllPropertiesSet() {
    CountryEntity entity = buildEntity();
    Country dto = countryConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getCountryId()).isEqualTo(36L);
    assertThat(dto.getCountryCode()).isEqualTo("AU");
    assertThat(dto.getCountryCode2()).isEqualTo("AUS");
    assertThat(dto.getName()).isEqualTo("Australia");
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    CountryEntity entity = CountryEntity.builder().build();
    Country dto = countryConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getCountryId()).isNull();
    assertThat(dto.getCountryCode()).isNull();
    assertThat(dto.getCountryCode2()).isNull();
    assertThat(dto.getName()).isNull();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Country dto = buildDto();
    CountryEntity entity = countryConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getCountryId()).isEqualTo(36L);
    assertThat(entity.getCountryCode1()).isEqualTo("AU");
    assertThat(entity.getCountryCode2()).isEqualTo("AUS");
    assertThat(entity.getCountryName()).isEqualTo("Australia");
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Country dto = Country.builder().build();
    CountryEntity entity = countryConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getCountryId()).isNull();
    assertThat(entity.getCountryCode1()).isNull();
    assertThat(entity.getCountryCode2()).isNull();
    assertThat(entity.getCountryName()).isNull();
  }

  @Test
  void testCopyDtoToEntity_withAllPropertiesSet() {
    Country dto = buildDto();
    CountryEntity entity = CountryEntity.builder().build();
    countryConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getCountryId()).isEqualTo(36L);
    assertThat(entity.getCountryCode1()).isEqualTo("AU");
    assertThat(entity.getCountryCode2()).isEqualTo("AUS");
    assertThat(entity.getCountryName()).isEqualTo("Australia");
  }

  @Test
  void testCopyDtoToEntity_withEmtpyDto() {
    Country dto = Country.builder().build();
    CountryEntity entity = buildEntity();
    countryConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getCountryId()).isNull();
    assertThat(entity.getCountryCode1()).isNull();
    assertThat(entity.getCountryCode2()).isNull();
    assertThat(entity.getCountryName()).isNull();
  }

  private CountryEntity buildEntity() {
    return CountryEntity.builder()
      .countryId(36L)
      .countryCode1("AU")
      .countryCode2("AUS")
      .countryName("Australia")
      .build();
  }

  private Country buildDto() {
    return Country.builder()
      .countryId(36L)
      .countryCode("AU")
      .countryCode2("AUS")
      .name("Australia")
      .build();
  }
}
