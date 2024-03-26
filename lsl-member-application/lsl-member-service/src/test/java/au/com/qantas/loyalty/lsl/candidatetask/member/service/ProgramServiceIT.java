package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.ApplicationIT;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProgramServiceIT extends ApplicationIT {

  @Autowired
  private ProgramService programService;

  @Test
  void testGetProgramsBy_willReturnExpectedPrograms() {
    Set<String> programIds = Set.of("ER", "FF", "PL");
    List<Program> result = programService.getProgramsBy(programIds);
    assertThat(result)
      .isNotNull()
      .isNotEmpty()
      .hasSize(3);
  }

  @Test
  void testGetProgramsBy_withNoExitedProgramIds_willReturnExpectedPrograms() {
    Set<String> programIds = Set.of("ER", "LL");
    List<Program> result1 = programService.getProgramsBy(programIds);
    assertThat(result1)
      .isNotNull()
      .isNotEmpty()
      .hasSize(1);

    programIds = Set.of("LL");
    List<Program> result2 = programService.getProgramsBy(programIds);
    assertThat(result2)
      .isNotNull()
      .isEmpty();
  }

  @Test
  void testGetProgramsBy_withEmtpyProgramIds_willReturnExpectedPrograms() {
    Set<String> programIds = Set.of();
    List<Program> result1 = programService.getProgramsBy(programIds);
    assertThat(result1)
      .isNotNull()
      .isEmpty();
  }

}
