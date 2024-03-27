package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class MemberConverter implements EntityConverter<MemberEntity, Member> {

  private ProgramConverter programConverter;

  @Autowired
  public MemberConverter(ProgramConverter programConverter) {
    this.programConverter = programConverter;
  }

  @Override
  public Member convertFromEntity(MemberEntity entity) {
    Set<Program> enrolledPrograms = new HashSet<>();
    if (entity.getEnrolledPrograms() != null) {
      entity.getEnrolledPrograms().forEach(programEntity ->
        enrolledPrograms.add(programConverter.convertFromEntity(programEntity))
      );
    }

    return Member.builder()
      .memberId(entity.getMemberId())
      .accountStatus(entity.getAccountStatus())
      .enrolledPrograms(enrolledPrograms)
      .firstName(entity.getGivenName())
      .lastName(entity.getSurname())
      .memberSince(entity.getEnrolledSince())
      .offerCategoryPreference(entity.getOfferCategoryPreference())
      .build();
  }

  @Override
  public MemberEntity convertToEntity(Member dto) {
    AccountStatus accountStatus = Optional.ofNullable(dto.getAccountStatus())
      .orElse(AccountStatus.PENDING);
    LocalDate enrolledSince = Optional.ofNullable(dto.getMemberSince()).orElse(LocalDate.now());
    Set<ProgramEntity> enrolledPrograms = new HashSet<>();
    if (dto.getEnrolledPrograms() != null) {
      dto.getEnrolledPrograms().forEach(program ->
        enrolledPrograms.add(programConverter.convertToEntity(program))
      );
    }
    return MemberEntity.builder()
      .memberId(dto.getMemberId())
      .accountStatus(accountStatus)
      .givenName(dto.getFirstName())
      .surname(dto.getLastName())
      .enrolledSince(enrolledSince)
      .offerCategoryPreference(dto.getOfferCategoryPreference())
      .enrolledPrograms(enrolledPrograms)
      .build();
  }

  @Override
  public void copyDtoToEntity(Member dto, MemberEntity entity) {
    ProgramConverter programConverter = new ProgramConverter();

    AccountStatus accountStatus = Optional.ofNullable(dto.getAccountStatus())
      .orElse(AccountStatus.PENDING);
    LocalDate enrolledSince = Optional.ofNullable(dto.getMemberSince()).orElse(LocalDate.now());
    Set<ProgramEntity> enrolledPrograms = new HashSet<>();

    if (dto.getEnrolledPrograms() != null) {
      dto.getEnrolledPrograms().forEach(program ->
        enrolledPrograms.add(programConverter.convertToEntity(program))
      );
    }

    entity.setMemberId(dto.getMemberId());
    entity.setAccountStatus(accountStatus);
    entity.setGivenName(dto.getFirstName());
    entity.setSurname(dto.getLastName());
    entity.setEnrolledSince(enrolledSince);
    entity.setEnrolledPrograms(enrolledPrograms);
    entity.setOfferCategoryPreference(dto.getOfferCategoryPreference());
  }
}
