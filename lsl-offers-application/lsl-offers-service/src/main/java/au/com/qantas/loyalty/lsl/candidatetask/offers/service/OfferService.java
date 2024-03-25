package au.com.qantas.loyalty.lsl.candidatetask.offers.service;

import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import au.com.qantas.loyalty.lsl.candidatetask.offers.repository.OfferRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OfferService {

  private OfferRepository offerRepository;

  @Autowired
  public OfferService(final OfferRepository offerRepository) {
    this.offerRepository = offerRepository;
  }

  public List<Offer> getOffers() {

    log.info("Finding all offers");

    final List<OfferEntity> foundOffers = offerRepository.findAll();

    log.info("Found '{}' offers", foundOffers.size());

    return foundOffers.stream()
        .map(offerEntity -> Offer.builder()
            .id(offerEntity.getOfferId())
            .name(offerEntity.getOfferName())
            .category(offerEntity.getOfferCategory())
            .description(offerEntity.getOfferDescription())
            .build())
        .toList();
  }

  public Optional<Offer> getOffer(final Long offerId) {

    log.info("Finding a offer by id '{}'", offerId);

    final Optional<OfferEntity> optionalFoundOfferEntity = offerRepository.findById(offerId);

    if (optionalFoundOfferEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a offer by id '{}'", offerId);

    final OfferEntity offerEntity = optionalFoundOfferEntity.get();

    return Optional.of(Offer.builder()
        .id(offerEntity.getOfferId())
        .name(offerEntity.getOfferName())
        .category(offerEntity.getOfferCategory())
        .description(offerEntity.getOfferDescription())
        .build());
  }
}
