package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import org.springframework.stereotype.Component;

@Component
public class ProgramConverter implements EntityConverter<ProgramEntity, Program> {

  @Override
  public Program convertFromEntity(ProgramEntity entity) {
    return Program.builder()
      .programId(entity.getProgramId())
      .marketingName(entity.getProgramName())
      .summaryDescription(entity.getProgramDescription())
      .build();
  }

  @Override
  public ProgramEntity convertToEntity(Program dto) {
    return ProgramEntity.builder()
      .programId(dto.getProgramId())
      .programName(dto.getMarketingName())
      .programDescription(dto.getSummaryDescription())
      .build();
  }

  @Override
  public void convertDtoToEntity(Program dto, ProgramEntity entity) {
    entity.setProgramId(dto.getProgramId());
    entity.setProgramName(dto.getMarketingName());
    entity.setProgramDescription(dto.getSummaryDescription());
  }
}
