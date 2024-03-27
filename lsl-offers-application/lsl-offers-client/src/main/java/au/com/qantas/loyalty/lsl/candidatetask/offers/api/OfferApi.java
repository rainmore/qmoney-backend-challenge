package au.com.qantas.loyalty.lsl.candidatetask.offers.api;

import static au.com.qantas.loyalty.lsl.candidatetask.offers.OpenApi.OFFERS_API_TAG;

import au.com.qantas.loyalty.lsl.candidatetask.api.DefaultApiErrorResponses;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.OpenApi;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = OFFERS_API_TAG)
@DefaultApiErrorResponses
public interface OfferApi {

  @GetMapping(value = OpenApi.OFFER_URL)
  @Operation(summary = "Retrieve all available offers",
      description = "This API returns the offer information such as id, name and description.")
  @ApiResponse(
      responseCode = "200",
      description = "The response payload contains the offer information.",
      content = @Content(schema = @Schema(implementation = Offer.class)))
  List<Offer> getOffers();

  @GetMapping(value = OpenApi.OFFER_OFFER_CATEGORY_URL)
  @Operation(summary = "Retrieve the offer information by offer-category",
    description = "This API returns the offer information such as id, name and description.")
  @ApiResponse(
    responseCode = "200",
    description = "The response payload contains the offer information.",
    content = @Content(schema = @Schema(implementation = Offer.class)))
  List<Offer> getOffers(
    @PathVariable
    @Parameter(name = "offerCategory", example = "NATURE") final OfferCategory offerCategory);

  @GetMapping(value = OpenApi.OFFER_OFFER_ID_URL)
  @Operation(summary = "Retrieve the offer information by offer-id",
      description = "This API returns the offer information such as id, name and description.")
  @ApiResponse(
      responseCode = "200",
      description = "The response payload contains the offer information.",
      content = @Content(schema = @Schema(implementation = Offer.class)))
  Offer getOffer(
      @PathVariable
      @Parameter(name = "offerId", example = "220010") final Long offerId);

}
