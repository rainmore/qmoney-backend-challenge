package au.com.qantas.loyalty.lsl.candidatetask.member.client;

import au.com.qantas.loyalty.lsl.candidatetask.offers.api.OfferApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "lsl-offers-client",
    url = "${feign.lsl-offers-client.url}")
public interface OfferClient extends OfferApi {

}
