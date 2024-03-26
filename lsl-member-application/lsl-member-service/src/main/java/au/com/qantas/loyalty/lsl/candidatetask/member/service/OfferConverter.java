package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import org.springframework.stereotype.Service;

@Service
public class OfferConverter implements EntityConverter<Offer, Member.Offer> {

  @Override
  public Member.Offer convertFromEntity(Offer entity) {
    return Member.Offer.builder()
      .id(entity.getId())
      .name(entity.getName())
      .description(entity.getDescription())
      .build();
  }

  @Override
  public Offer convertToEntity(Member.Offer dto) {
    return Offer.builder()
      .id(dto.getId())
      .name(dto.getName())
      .description(dto.getDescription())
      .build();
  }

  @Override
  public void convertDtoToEntity(Member.Offer dto, Offer entity) {
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
  }
}
