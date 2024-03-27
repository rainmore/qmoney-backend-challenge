package au.com.qantas.loyalty.lsl.candidatetask.offers.api;

import au.com.qantas.loyalty.lsl.candidatetask.api.InternalServerException;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.OpenApi;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import au.com.qantas.loyalty.lsl.candidatetask.offers.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OfferControllerIT extends AbstractControllerIT {

  private MockMvc mockMvc;

  @MockBean
  private OfferService offerService;

  @Autowired
  private WebApplicationContext wac;

  @BeforeEach
  void beforeEach() {
    this.mockMvc = MockMvcBuilders
      .webAppContextSetup(this.wac)
      .build();
  }

  @Test
  void testGetOffersServiceRequest_byOfferCategory_shouldReturnHttpStatus200() throws Exception {
    final OfferCategory offerCategory = OfferCategory.NATURE;
    final List<Offer> results = buildOffer().stream().filter(offer -> offer.getCategory().equals(offerCategory)).toList();

    when(offerService.getOffers(offerCategory)).thenReturn(results);

    mockMvc.perform(
        get(OpenApi.OFFER_OFFER_CATEGORY_URL, offerCategory)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id", is(220011)))
      .andExpect(jsonPath("$[0].name", is("Blossom Festival")))
      .andExpect(jsonPath("$[0].category", is("NATURE")))
      .andExpect(jsonPath("$[0].description", is("Enjoy the Spring blossoms in a variety of Asian locations.")))
      .andExpect(jsonPath("$[1].id", is(220012)))
      .andExpect(jsonPath("$[1].name", is("Autumn Leaves")))
      .andExpect(jsonPath("$[1].category", is("NATURE")))
      .andExpect(jsonPath("$[1].description", is("Enjoy the autumn leaves of Canada.")));
  }

  @Test
  void testGetOffersServiceRequest_byOfferCategory_shouldReturnHttpStatus404() throws Exception {
    final OfferCategory offerCategory = OfferCategory.NATURE;
    final List<Offer> results = new ArrayList<>();

    when(offerService.getOffers(offerCategory)).thenReturn(results);

    mockMvc.perform(
        get(OpenApi.OFFER_OFFER_CATEGORY_URL, offerCategory)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void testGetOffersServiceRequest_byOfferCategory_shouldReturnHttpStatus500() throws Exception {
    final OfferCategory offerCategory = OfferCategory.NATURE;
    final InternalServerException exception = new InternalServerException("Internal System Error");

    when(offerService.getOffers(offerCategory)).thenThrow(exception);

    mockMvc.perform(
        get(OpenApi.OFFER_OFFER_CATEGORY_URL, offerCategory)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isInternalServerError());
  }

  private List<Offer> buildOffer() {
    List<Offer> entities = new ArrayList<>();
    entities.add(Offer.builder()
      .id(220011L)
      .name("Blossom Festival")
      .category(OfferCategory.NATURE)
      .description("Enjoy the Spring blossoms in a variety of Asian locations.")
      .build());
    entities.add(Offer.builder()
      .id(220012L)
      .name("Autumn Leaves")
      .category(OfferCategory.NATURE)
      .description("Enjoy the autumn leaves of Canada.")
      .build());
    entities.add(Offer.builder()
      .id(220013L)
      .name("Ski Slopes")
      .category(OfferCategory.SNOW)
      .description("Pack your warm skis and snowboards for a snow adventure.")
      .build());
    return entities;
  }
}
