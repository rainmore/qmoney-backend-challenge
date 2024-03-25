package au.com.qantas.loyalty.lsl.candidatetask.offers.api;

import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import au.com.qantas.loyalty.lsl.candidatetask.offers.service.OfferService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController implements OfferApi {

  private final OfferService offerService;

  @Autowired
  OfferController(final OfferService offerService) {
    this.offerService = offerService;
  }

  @Override
  public List<Offer> getOffers() {
    final List<Offer> foundOffers = offerService.getOffers();

    if (foundOffers.isEmpty()) {
      throw new ResourceNotFoundException("No offers exist.");
    }

    return foundOffers;
  }

  @Override
  public Offer getOffer(final Long offerId) {

    final Optional<Offer> foundOffer = offerService.getOffer(offerId);

    if (foundOffer.isEmpty()) {
      throw new ResourceNotFoundException("No offer exists with offerId=" + offerId);
    }

    return foundOffer.get();
  }
}
