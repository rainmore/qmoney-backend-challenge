package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;

interface ProgramRepositoryCustom {

  ProgramEntity findDefaultProgram();

  Iterable<ProgramEntity> findAllByMemberId(final Long memberId);

}
