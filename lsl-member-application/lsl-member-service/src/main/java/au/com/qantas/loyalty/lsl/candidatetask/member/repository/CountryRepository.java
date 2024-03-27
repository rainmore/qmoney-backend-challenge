package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long>,
  QuerydslPredicateExecutor<CountryEntity> {
}
