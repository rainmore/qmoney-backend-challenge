package au.com.qantas.loyalty.lsl.candidatetask.offers.repository;

import au.com.qantas.loyalty.lsl.candidatetask.offers.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>,
  QuerydslPredicateExecutor<OfferEntity> {

}
