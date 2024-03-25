package au.com.qantas.loyalty.lsl.candidatetask.offers.repository;

import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<OfferEntity, Long> {

  @Override
  List<OfferEntity> findAll();

}
