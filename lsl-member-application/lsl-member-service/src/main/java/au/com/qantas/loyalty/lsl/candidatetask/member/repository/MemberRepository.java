package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MemberEntity, Long> {

}
