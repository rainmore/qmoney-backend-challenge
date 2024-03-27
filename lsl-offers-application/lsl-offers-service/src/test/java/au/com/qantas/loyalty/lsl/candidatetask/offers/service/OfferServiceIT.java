package au.com.qantas.loyalty.lsl.candidatetask.offers.service;

import au.com.qantas.loyalty.lsl.candidatetask.ApplicationIT;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OfferServiceIT extends ApplicationIT {

  @Autowired
  private OfferService offerService;


  @Test
  void testGetOffers_returnListEntity() {
    List<Offer> results = offerService.getOffers(OfferCategory.NATURE);
    assertThat(results)
      .isNotNull()
      .isNotEmpty()
      .hasSize(2);

    Set<OfferCategory> categorySet = results.stream().map(Offer::getCategory).collect(Collectors.toSet());
    assertThat(categorySet)
      .isNotNull()
      .isNotEmpty()
      .hasSize(1)
      .containsAll(Set.of(OfferCategory.NATURE));
  }

}
