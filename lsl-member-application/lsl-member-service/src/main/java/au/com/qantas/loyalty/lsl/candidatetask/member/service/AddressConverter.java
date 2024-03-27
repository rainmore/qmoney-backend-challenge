package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.AddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Address;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Country;
import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressConverter implements EntityConverter<AddressEntity, Address> {

  private final CountryConverter countryConverter;

  @Autowired
  public AddressConverter(CountryConverter countryConverter) {
    this.countryConverter = countryConverter;
  }

  @Override
  public Address convertFromEntity(AddressEntity entity) {
    Country country = Optional.ofNullable(entity.getCountry())
      .map(countryConverter::convertFromEntity)
      .orElse(null);
    return Address.builder()
      .addressId(entity.getAddressId())
      .address1(entity.getAddress1())
      .address2(entity.getAddress2())
      .city(entity.getCity())
      .state(entity.getState())
      .postcode(entity.getPostcode())
      .country(country)
      .build();
  }

  @Override
  public AddressEntity convertToEntity(Address dto) {
    CountryEntity countryEntity = Optional.ofNullable(dto.getCountry())
      .map(countryConverter::convertToEntity)
      .orElse(null);
    return AddressEntity.builder()
      .addressId(dto.getAddressId())
      .address1(dto.getAddress1())
      .address2(dto.getAddress2())
      .city(dto.getCity())
      .state(dto.getState())
      .postcode(dto.getPostcode())
      .country(countryEntity)
      .build();
  }

  @Override
  public void copyDtoToEntity(Address dto, AddressEntity entity) {
    CountryEntity countryEntity = Optional.ofNullable(dto.getCountry())
      .map(countryConverter::convertToEntity)
      .orElse(null);
      entity.setAddressId(dto.getAddressId());
      entity.setAddress1(dto.getAddress1());
      entity.setAddress2(dto.getAddress2());
      entity.setCity(dto.getCity());
      entity.setState(dto.getState());
      entity.setPostcode(dto.getPostcode());
      entity.setCountry(countryEntity);
  }
}
