package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProgramService {

  public static final String FREQUENT_FLYER_PROGRAM = "FF";

  private ProgramRepository programRepository;

  @Autowired
  public ProgramService(final ProgramRepository programRepository) {
    this.programRepository = programRepository;
  }

  public List<Program> getPrograms() {

    log.info("Finding all programs");

    final List<ProgramEntity> foundPrograms = programRepository.findAll();

    log.info("Found '{}' programs", foundPrograms.size());

    return foundPrograms.stream()
        .map(programEntity -> Program.builder()
            .programId(programEntity.getProgramId())
            .marketingName(programEntity.getProgramName())
            .summaryDescription(programEntity.getProgramDescription())
            .build())
        .toList();
  }

  public Optional<Program> getProgram(final String programId) {

    log.info("Finding a program by id '{}'", programId);

    final Optional<ProgramEntity> optionalFoundProgramEntity = programRepository.findById(programId);

    if (optionalFoundProgramEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a program by id '{}'", programId);

    final ProgramEntity foundProgramEntity = optionalFoundProgramEntity.get();

    return Optional.of(Program.builder()
        .programId(foundProgramEntity.getProgramId())
        .marketingName(foundProgramEntity.getProgramName())
        .summaryDescription(foundProgramEntity.getProgramDescription())
        .build());
  }
}
