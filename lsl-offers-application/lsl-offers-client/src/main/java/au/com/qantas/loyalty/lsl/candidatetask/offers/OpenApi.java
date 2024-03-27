package au.com.qantas.loyalty.lsl.candidatetask.offers;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class OpenApi {

  public static final String OFFERS_API_TAG = "Offers APIs";

  public static final String OFFER_URL = "/offer";
  public static final String OFFER_OFFER_ID_URL = "/offer/{offerId}";

  public static final String OFFER_OFFER_CATEGORY_URL = "/offer/{offerCategory}";

}
