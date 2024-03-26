package au.com.qantas.loyalty.lsl.candidatetask.member.repository;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.QMemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.QProgramEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Slf4j
public class ProgramRepositoryImpl extends QuerydslRepositorySupport
  implements ProgramRepositoryCustom {

  private static final String FREQUENT_FLYER_PROGRAM = "FF";

  public ProgramRepositoryImpl() {
    super(ProgramEntity.class);
  }

  @Override
  public ProgramEntity findDefaultProgram() {
    QProgramEntity qProgramEntity = QProgramEntity.programEntity;
    BooleanExpression criteria = qProgramEntity.programId.eq(FREQUENT_FLYER_PROGRAM);
    return from(qProgramEntity)
      .where(criteria)
      .fetchFirst();
  }

  @Override
  public Iterable<ProgramEntity> findAllByMemberId(Long memberId) {
    QMemberEntity qMemberEntity = QMemberEntity.memberEntity;
    QProgramEntity qProgramEntity = QProgramEntity.programEntity;
    BooleanExpression criteria = qMemberEntity.memberId.eq(memberId);

    return from(qMemberEntity)
      .join(qMemberEntity.enrolledPrograms, qProgramEntity)
      .where(criteria)
      .select(qProgramEntity)
      .fetch();
  }

}
