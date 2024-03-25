package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberProgramEntity.MemberProgramId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MemberProgramRepository extends CrudRepository<MemberProgramEntity, MemberProgramId> {

  @Query("SELECT mp FROM MemberProgramEntity mp WHERE mp.memberId = :memberId")
  Iterable<MemberProgramEntity> findAllByMemberId(@Param("memberId") final Long memberId);
}
