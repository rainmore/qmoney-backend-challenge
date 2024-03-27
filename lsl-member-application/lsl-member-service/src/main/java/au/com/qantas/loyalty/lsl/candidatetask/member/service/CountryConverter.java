package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Country;
import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter implements EntityConverter<CountryEntity, Country> {

  @Override
  public Country convertFromEntity(CountryEntity entity) {
    return Country.builder()
      .countryId(entity.getCountryId())
      .countryCode(entity.getCountryCode1())
      .countryCode2(entity.getCountryCode2())
      .name(entity.getCountryName())
      .build();
  }

  @Override
  public CountryEntity convertToEntity(Country dto) {
    return CountryEntity.builder()
      .countryId(dto.getCountryId())
      .countryCode1(dto.getCountryCode())
      .countryCode2(dto.getCountryCode2())
      .countryName(dto.getName())
      .build();
  }

  @Override
  public void copyDtoToEntity(Country dto, CountryEntity entity) {
    entity.setCountryId(dto.getCountryId());
    entity.setCountryName(dto.getName());
    entity.setCountryCode1(dto.getCountryCode());
    entity.setCountryCode2(dto.getCountryCode2());
  }
}
