package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgramConverterTest {

  private ProgramConverter programConverter;

  @BeforeEach
  void setup() {
    programConverter = new ProgramConverter();
  }

  @Test
  void testConvertFromEntity_withAllPropertiesSet() {
    ProgramEntity entity = buildEntity();
    Program dto = programConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getProgramId()).isEqualTo("FF");
    assertThat(dto.getMarketingName()).isEqualTo("Frequent Flyer");
    assertThat(dto.getSummaryDescription()).isEqualTo("Frequent Flyer program");
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    ProgramEntity entity = ProgramEntity.builder().build();
    Program dto = programConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getProgramId()).isNull();
    assertThat(dto.getMarketingName()).isNull();
    assertThat(dto.getSummaryDescription()).isNull();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Program dto = buildDto();
    ProgramEntity entity = programConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getProgramId()).isEqualTo("FF");
    assertThat(entity.getProgramName()).isEqualTo("Frequent Flyer");
    assertThat(entity.getProgramDescription()).isEqualTo("Frequent Flyer program");
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Program dto = Program.builder().build();
    ProgramEntity entity = programConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getProgramId()).isNull();
    assertThat(entity.getProgramName()).isNull();
    assertThat(entity.getProgramDescription()).isNull();
  }

  @Test
  void testConvertDtoToEntity_withAllPropertiesSet() {
    Program dto = buildDto();
    ProgramEntity entity = ProgramEntity.builder().build();
    programConverter.convertDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getProgramId()).isEqualTo("FF");
    assertThat(entity.getProgramName()).isEqualTo("Frequent Flyer");
    assertThat(entity.getProgramDescription()).isEqualTo("Frequent Flyer program");
  }

  @Test
  void testConvertDtoToEntity_withEmtpyDto() {
    Program dto = Program.builder().build();
    ProgramEntity entity = buildEntity();
    programConverter.convertDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getProgramId()).isNull();
    assertThat(entity.getProgramName()).isNull();
    assertThat(entity.getProgramDescription()).isNull();
  }

  private ProgramEntity buildEntity() {
    return ProgramEntity.builder()
      .programId("FF")
      .programName("Frequent Flyer")
      .programDescription("Frequent Flyer program")
      .build();
  }

  private Program buildDto() {
    return Program.builder()
      .programId("FF")
      .marketingName("Frequent Flyer")
      .summaryDescription("Frequent Flyer program")
      .build();
  }
}
