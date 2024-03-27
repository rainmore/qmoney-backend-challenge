package au.com.qantas.loyalty.lsl.candidatetask.offers.service;

import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import org.springframework.stereotype.Component;

@Component
public class OfferConverter implements EntityConverter<OfferEntity, Offer> {

  @Override
  public Offer convertFromEntity(OfferEntity entity) {
    return Offer.builder()
      .id(entity.getOfferId())
      .name(entity.getOfferName())
      .category(entity.getOfferCategory())
      .description(entity.getOfferDescription())
      .build();
  }

  @Override
  public OfferEntity convertToEntity(Offer dto) {
    return OfferEntity.builder()
      .offerId(dto.getId())
      .offerName(dto.getName())
      .offerCategory(dto.getCategory())
      .offerDescription(dto.getDescription())
      .build();
  }

  @Override
  public void copyDtoToEntity(Offer dto, OfferEntity entity) {
    entity.setOfferId(dto.getId());
    entity.setOfferName(dto.getName());
    entity.setOfferCategory(dto.getCategory());
    entity.setOfferDescription(dto.getDescription());
  }
}
