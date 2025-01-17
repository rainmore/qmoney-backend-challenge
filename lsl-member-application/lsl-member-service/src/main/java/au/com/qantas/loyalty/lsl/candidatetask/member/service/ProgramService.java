package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ProgramService {

  private ProgramRepository programRepository;

  private ProgramConverter programConverter;

  @Autowired
  public ProgramService(
    final ProgramRepository programRepository,
    final ProgramConverter programConverter
  ) {
    this.programRepository = programRepository;
    this.programConverter = programConverter;
  }

  public List<Program> getPrograms() {

    log.info("Finding all programs");

    final List<ProgramEntity> foundPrograms = programRepository.findAll();

    log.info("Found '{}' programs", foundPrograms.size());

    return foundPrograms.stream()
        .map(programConverter::convertFromEntity)
        .toList();
  }

  public List<Program> getProgramsBy(final Set<String> programIds) {
    log.info("Finding all programs");
    final Iterable<ProgramEntity> foundPrograms = programRepository.findAllById(programIds);

    List<Program> programs = StreamSupport.stream(foundPrograms.spliterator(), false)
      .map(programConverter::convertFromEntity)
      .toList();

    log.info("Found '{}' programs", programs.size());
    return programs;
  }

  public Optional<Program> getProgram(final String programId) {

    log.info("Finding a program by id '{}'", programId);

    final Optional<ProgramEntity> optionalFoundProgramEntity = programRepository.findById(programId);

    if (optionalFoundProgramEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a program by id '{}'", programId);

    return optionalFoundProgramEntity.map(programConverter::convertFromEntity);
  }
}
