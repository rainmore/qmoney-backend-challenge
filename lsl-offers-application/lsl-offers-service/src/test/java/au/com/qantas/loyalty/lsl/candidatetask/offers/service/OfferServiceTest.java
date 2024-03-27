package au.com.qantas.loyalty.lsl.candidatetask.offers.service;

import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.QOfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import au.com.qantas.loyalty.lsl.candidatetask.offers.repository.OfferRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

  @Mock
  private OfferRepository offerRepository;

  private OfferService offerService;

  @BeforeEach
  void setup() {
    offerService = new OfferService(
      offerRepository,
      new OfferConverter()
    );
  }

  @Test
  void testGetOffers_byOfferCategory_returnListEntity() {
    OfferCategory offerCategory = OfferCategory.NATURE;
    BooleanExpression criteria = QOfferEntity.offerEntity.offerCategory.eq(offerCategory);

    List<OfferEntity> expectedEntity = buildOfferEntities()
      .stream().filter(offerEntity -> offerEntity.getOfferCategory().equals(offerCategory)).toList();

    when(offerRepository.findAll(criteria)).thenReturn(expectedEntity);

    List<Offer> results = offerService.getOffers(offerCategory);

    assertThat(results)
      .isNotNull()
      .isNotEmpty()
      .hasSize(2);
  }

  @Test
  void testGetOffers_byOfferCategory_throwExceptionWhenNoEntityFound() {
    OfferCategory offerCategory = OfferCategory.BEACH;
    BooleanExpression criteria = QOfferEntity.offerEntity.offerCategory.eq(offerCategory);
    when(offerRepository.findAll(criteria)).thenReturn(new ArrayList<>());

    List<Offer> results = offerService.getOffers(offerCategory);
    assertThat(results)
      .isNotNull()
      .isEmpty();
  }

  private List<OfferEntity> buildOfferEntities() {
    List<OfferEntity> entities = new ArrayList<>();
    entities.add(OfferEntity.builder()
        .offerId(220011L)
        .offerName("Blossom Festival")
        .offerCategory(OfferCategory.NATURE)
        .offerDescription("Enjoy the Spring blossoms in a variety of Asian locations.")
      .build());
    entities.add(OfferEntity.builder()
      .offerId(220012L)
      .offerName("Autumn Leaves")
      .offerCategory(OfferCategory.NATURE)
      .offerDescription("Enjoy the autumn leaves of Canada.")
      .build());
    entities.add(OfferEntity.builder()
      .offerId(220013L)
      .offerName("Ski Slopes")
      .offerCategory(OfferCategory.SNOW)
      .offerDescription("Pack your warm skis and snowboards for a snow adventure.")
      .build());
    return entities;
  }
}
