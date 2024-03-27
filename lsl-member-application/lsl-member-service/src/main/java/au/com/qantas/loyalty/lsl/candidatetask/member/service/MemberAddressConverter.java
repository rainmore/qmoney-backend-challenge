package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.AddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressPK;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.AddressCategory;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberAddressConverter implements EntityConverter<MemberAddressEntity, Member.Address> {

  @Override
  public Member.Address convertFromEntity(MemberAddressEntity entity) {
    if (entity.getId() == null || entity.getId().getAddress() == null || entity.getId().getCategory() == null) {
      return Member.Address.builder().build();
    }

    AddressEntity addressEntity = entity.getId().getAddress();

    return Member.Address.builder()
      .id(addressEntity.getAddressId())
      .address1(addressEntity.getAddress1())
      .address2(addressEntity.getAddress2())
      .city(addressEntity.getCity())
      .state(addressEntity.getState())
      .postcode(addressEntity.getPostcode())
      .country(Optional.ofNullable(addressEntity.getCountry())
        .map(CountryEntity::getCountryName)
        .orElse(null))
      .category(entity.getId().getCategory())
      .build();
  }

  @Override
  public MemberAddressEntity convertToEntity(Member.Address dto) {
    return MemberAddressEntity.builder()
      .id(MemberAddressPK.builder()
        .category(Optional.ofNullable(dto.getCategory()).orElse(AddressCategory.RESIDENTIAL))
        .address(AddressEntity.builder()
          .addressId(dto.getId())
          .address1(dto.getAddress1())
          .address2(dto.getAddress2())
          .city(dto.getCity())
          .state(dto.getState())
          .postcode(dto.getPostcode())
          .country(Optional.ofNullable(dto.getCountry())
            .map(country -> CountryEntity.builder()
              .countryName(country)
              .build())
            .orElse(null))
          .build())
        .build())
      .build();
  }

  @Override
  public void copyDtoToEntity(Member.Address dto, MemberAddressEntity entity) {
    if (entity.getId() == null) {
      entity.setId(new MemberAddressPK());
    }

    if (entity.getId().getAddress() == null) {
      entity.getId().setAddress(new AddressEntity());
    }

    entity.getId().getAddress().setAddressId(dto.getId());
    entity.getId().getAddress().setAddress1(dto.getAddress1());
    entity.getId().getAddress().setAddress2(dto.getAddress2());
    entity.getId().getAddress().setCity(dto.getCity());
    entity.getId().getAddress().setState(dto.getState());
    entity.getId().getAddress().setPostcode(dto.getPostcode());
    entity.getId().getAddress().setCountry(Optional.ofNullable(dto.getCountry())
      .map(country -> CountryEntity.builder()
        .countryName(country)
        .build())
      .orElse(null));
    entity.getId().setCategory(dto.getCategory());
  }
}
