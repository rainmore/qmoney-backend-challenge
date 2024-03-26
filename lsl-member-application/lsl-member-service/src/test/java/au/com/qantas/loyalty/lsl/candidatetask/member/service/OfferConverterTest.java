package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
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
    Offer entity = buildEntity();
    Member.Offer dto = offerConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(3344L);
    assertThat(dto.getName()).isEqualTo("Autumn Leaves");
    assertThat(dto.getDescription()).isEqualTo("See the autumn leaves of Canada");
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    Offer entity = Offer.builder().build();
    Member.Offer dto = offerConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNull();
    assertThat(dto.getName()).isNull();
    assertThat(dto.getDescription()).isNull();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Member.Offer dto = buildDto();
    Offer entity = offerConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isEqualTo(3344L);
    assertThat(entity.getName()).isEqualTo("Autumn Leaves");
    assertThat(entity.getDescription()).isEqualTo("See the autumn leaves of Canada");
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Member.Offer dto = Member.Offer.builder().build();
    Offer entity = offerConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNull();
    assertThat(entity.getName()).isNull();
    assertThat(entity.getDescription()).isNull();
  }

  @Test
  void testConvertDtoToEntity_withAllPropertiesSet() {
    Member.Offer dto = buildDto();
    Offer entity = Offer.builder().build();
    offerConverter.convertDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isEqualTo(3344L);
    assertThat(entity.getName()).isEqualTo("Autumn Leaves");
    assertThat(entity.getDescription()).isEqualTo("See the autumn leaves of Canada");
  }

  @Test
  void testConvertDtoToEntity_withEmtpyDto() {
    Member.Offer dto = Member.Offer.builder().build();
    Offer entity = buildEntity();
    offerConverter.convertDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getId()).isNull();
    assertThat(entity.getName()).isNull();
    assertThat(entity.getDescription()).isNull();
  }

  private Offer buildEntity() {
    return Offer.builder()
      .id(3344L)
      .name("Autumn Leaves")
      .description("See the autumn leaves of Canada")
      .build();
  }

  private Member.Offer buildDto() {
    return Member.Offer.builder()
      .id(3344L)
      .name("Autumn Leaves")
      .description("See the autumn leaves of Canada")
      .build();
  }
}
