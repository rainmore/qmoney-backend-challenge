package au.com.qantas.loyalty.lsl.candidatetask.offers.service;

import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OfferConverterTest {

  private OfferConverter offerConverter;

  @BeforeEach
  void setup() {
    offerConverter = new OfferConverter();
  }

  @Test
  void testConvertFromEntity_withAllPropertiesSet() {
    OfferEntity entity = buildEntity();
    Offer dto = offerConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(3344L);
    assertThat(dto.getName()).isEqualTo("Autumn Leaves");
    assertThat(dto.getCategory()).isEqualTo(OfferCategory.NATURE);
    assertThat(dto.getDescription()).isEqualTo("See the autumn leaves of Canada");
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    OfferEntity entity = OfferEntity.builder().build();
    Offer dto = offerConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNull();
    assertThat(dto.getName()).isNull();
    assertThat(dto.getCategory()).isNull();
    assertThat(dto.getDescription()).isNull();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Offer dto = buildDto();
    OfferEntity entity = offerConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getOfferId()).isEqualTo(3344L);
    assertThat(entity.getOfferName()).isEqualTo("Autumn Leaves");
    assertThat(entity.getOfferCategory()).isEqualTo(OfferCategory.NATURE);
    assertThat(entity.getOfferDescription()).isEqualTo("See the autumn leaves of Canada");
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Offer dto = Offer.builder().build();
    OfferEntity entity = offerConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getOfferId()).isNull();
    assertThat(entity.getOfferName()).isNull();
    assertThat(entity.getOfferCategory()).isNull();
    assertThat(entity.getOfferDescription()).isNull();
  }

  @Test
  void testCopyDtoToEntity_withAllPropertiesSet() {
    Offer dto = buildDto();
    OfferEntity entity = OfferEntity.builder().build();
    offerConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getOfferId()).isEqualTo(3344L);
    assertThat(entity.getOfferName()).isEqualTo("Autumn Leaves");
    assertThat(entity.getOfferCategory()).isEqualTo(OfferCategory.NATURE);
    assertThat(entity.getOfferDescription()).isEqualTo("See the autumn leaves of Canada");
  }

  @Test
  void testCopyDtoToEntity_withEmtpyDto() {
    Offer dto = Offer.builder().build();
    OfferEntity entity = buildEntity();
    offerConverter.copyDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getOfferId()).isNull();
    assertThat(entity.getOfferName()).isNull();
    assertThat(entity.getOfferCategory()).isNull();
    assertThat(entity.getOfferDescription()).isNull();
  }

  private OfferEntity buildEntity() {
    return OfferEntity.builder()
      .offerId(3344L)
      .offerName("Autumn Leaves")
      .offerCategory(OfferCategory.NATURE)
      .offerDescription("See the autumn leaves of Canada")
      .build();
  }

  private Offer buildDto() {
    return Offer.builder()
      .id(3344L)
      .name("Autumn Leaves")
      .category(OfferCategory.NATURE)
      .description("See the autumn leaves of Canada")
      .build();
  }
}
