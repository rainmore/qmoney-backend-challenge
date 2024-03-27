package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAddressRepository extends JpaRepository<MemberAddressEntity, MemberAddressPK>,
  QuerydslPredicateExecutor<MemberAddressEntity> {
}
