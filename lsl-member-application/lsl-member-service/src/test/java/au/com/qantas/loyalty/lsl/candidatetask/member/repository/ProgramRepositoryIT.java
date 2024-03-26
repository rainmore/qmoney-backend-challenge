package au.com.qantas.loyalty.lsl.candidatetask.member.repository;


import au.com.qantas.loyalty.lsl.candidatetask.ApplicationIT;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProgramRepositoryIT extends ApplicationIT {

  @Autowired
  private ProgramRepository programRepository;

  @Test
  void testFindDefaultProgram_returnEntity() {
    ProgramEntity result = programRepository.findDefaultProgram();
    assertThat(result)
      .isNotNull();

    assertThat(result.getProgramId()).isEqualTo("FF");
  }

  @Test
  void testFindAllByMemberId_returnEmptyWithInvalidMemberId() {
    Long invalidMemberId = 112233L;
    Iterable<ProgramEntity> result = programRepository.findAllByMemberId(invalidMemberId);
    assertThat(result)
      .isNotNull()
      .isEmpty();
  }

  @Test
  void testFindAllByMemberId_returnEntitiesWithValidMemberId() {
    Long validMemberId = 110020L;

    Iterable<ProgramEntity> result = programRepository.findAllByMemberId(validMemberId);
    assertThat(result)
      .isNotNull()
      .isNotEmpty()
    ;

    List<ProgramEntity> entityList = StreamSupport.stream(result.spliterator(), false).toList();

    List<String> programIds = entityList.stream().map(ProgramEntity::getProgramId).toList();

    assertThat(programIds).containsAll(List.of("ER", "FF", "PL"));
  }
}
