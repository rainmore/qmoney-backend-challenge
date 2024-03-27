package au.com.qantas.loyalty.lsl.candidatetask.offers.service;

import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.QOfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import au.com.qantas.loyalty.lsl.candidatetask.offers.repository.OfferRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class OfferService {

  private OfferRepository offerRepository;

  private OfferConverter offerConverter;

  @Autowired
  public OfferService(final OfferRepository offerRepository,
                      final OfferConverter offerConverter) {
    this.offerRepository = offerRepository;
    this.offerConverter = offerConverter;
  }

  public List<Offer> getOffers() {

    log.info("Finding all offers");

    final List<OfferEntity> foundOffers = offerRepository.findAll();

    log.info("Found '{}' offers", foundOffers.size());

    return foundOffers.stream()
      .map(offerConverter::convertFromEntity)
      .toList();
  }

  public List<Offer> getOffers(OfferCategory offerCategory) {

    log.info("Finding all offers by offer category={}", offerCategory);

    final BooleanExpression criteria = QOfferEntity.offerEntity.offerCategory.eq(offerCategory);

    final Iterable<OfferEntity> foundOffers = offerRepository.findAll(criteria);

    List<Offer> offers = StreamSupport.stream(foundOffers.spliterator(), false)
      .map(offerConverter::convertFromEntity)
      .toList();

    log.info("Found '{}' offers", offers.size());

    return offers;
  }

  public Optional<Offer> getOffer(final Long offerId) {

    log.info("Finding a offer by id '{}'", offerId);

    final Optional<OfferEntity> optionalFoundOfferEntity = offerRepository.findById(offerId);

    if (optionalFoundOfferEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a offer by id '{}'", offerId);

    final OfferEntity offerEntity = optionalFoundOfferEntity.get();

    return Optional.of(offerConverter.convertFromEntity(offerEntity));
  }
}
