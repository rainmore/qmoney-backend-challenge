package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class MemberConverterTest {

  private MemberConverter memberConverter;

  @BeforeEach
  void setup() {
    memberConverter = new MemberConverter(new ProgramConverter());
  }

  @Test
  void testConvertFromEntity_withAllPropertiesSet() {
    MemberEntity entity = buildEntity();
    Member dto = memberConverter.convertFromEntity(entity);

    assertThat(dto).isNotNull();
    assertThat(dto.getMemberId()).isEqualTo(112233L);
    assertThat(dto.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
    assertThat(dto.getFirstName()).isEqualTo("Fred");
    assertThat(dto.getLastName()).isEqualTo("Flintstone");
    assertThat(dto.getMemberSince()).isEqualTo(LocalDate.of(2017, 07, 26));
    assertThat(dto.getOfferCategoryPreference()).isEqualTo(OfferCategory.NATURE);

    assertThat(dto.getEnrolledPrograms())
      .isNotNull()
      .isNotEmpty()
      .hasSize(1);

    assertThat(dto.getEnrolledPrograms().stream().findFirst().get().getProgramId())
      .isEqualTo("FF");
  }

  @Test
  void testConvertFromEntity_withEmtpyEntity() {
    MemberEntity entity = MemberEntity.builder().build();
    Member dto = memberConverter.convertFromEntity(entity);
    assertThat(dto).isNotNull();
    assertThat(dto.getMemberId()).isNull();
    assertThat(dto.getAccountStatus()).isNull();
    assertThat(dto.getFirstName()).isNull();
    assertThat(dto.getLastName()).isNull();
    assertThat(dto.getMemberSince()).isNull();
    assertThat(dto.getOfferCategoryPreference()).isNull();

    assertThat(dto.getEnrolledPrograms())
      .isNotNull()
      .isEmpty();
  }

  @Test
  void testConvertToEntity_withAllPropertiesSet() {
    Member dto = buildDto();
    MemberEntity entity = memberConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getMemberId()).isEqualTo(112233L);
    assertThat(entity.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
    assertThat(entity.getGivenName()).isEqualTo("Fred");
    assertThat(entity.getSurname()).isEqualTo("Flintstone");
    assertThat(entity.getEnrolledSince()).isEqualTo(LocalDate.of(2017, 07, 26));
    assertThat(entity.getOfferCategoryPreference()).isEqualTo(OfferCategory.NATURE);

    assertThat(entity.getEnrolledPrograms())
      .isNotNull()
      .isNotEmpty()
      .hasSize(1);

    assertThat(entity.getEnrolledPrograms().stream().findFirst().get().getProgramId())
      .isEqualTo("FF");
  }

  @Test
  void testConvertToEntity_withEmtpyDto() {
    Member dto = Member.builder().build();
    MemberEntity entity = memberConverter.convertToEntity(dto);

    assertThat(entity).isNotNull();
    assertThat(entity.getMemberId()).isNull();
    assertThat(entity.getAccountStatus())
      .isNotNull()
      .isEqualTo(AccountStatus.PENDING);
    assertThat(entity.getGivenName()).isNull();
    assertThat(entity.getSurname()).isNull();
    assertThat(entity.getEnrolledSince()).isNotNull();
    assertThat(entity.getOfferCategoryPreference()).isNull();

    assertThat(entity.getEnrolledPrograms())
      .isNotNull()
      .isEmpty();
  }

  @Test
  void testConvertDtoToEntity_withAllPropertiesSet() {
    Member dto = buildDto();
    MemberEntity entity = MemberEntity.builder().build();
    memberConverter.convertDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getMemberId()).isEqualTo(112233L);
    assertThat(entity.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
    assertThat(entity.getGivenName()).isEqualTo("Fred");
    assertThat(entity.getSurname()).isEqualTo("Flintstone");
    assertThat(entity.getEnrolledSince()).isEqualTo(LocalDate.of(2017, 07, 26));
    assertThat(entity.getOfferCategoryPreference()).isEqualTo(OfferCategory.NATURE);

    assertThat(entity.getEnrolledPrograms())
      .isNotNull()
      .isNotEmpty()
      .hasSize(1);

    assertThat(entity.getEnrolledPrograms().stream().findFirst().get().getProgramId())
      .isEqualTo("FF");
  }

  @Test
  void testConvertDtoToEntity_withEmtpyDto() {
    Member dto = Member.builder().build();
    MemberEntity entity = buildEntity();
    memberConverter.convertDtoToEntity(dto, entity);

    assertThat(entity).isNotNull();
    assertThat(entity.getMemberId()).isNull();
    assertThat(entity.getAccountStatus())
      .isNotNull()
      .isEqualTo(AccountStatus.PENDING);
    assertThat(entity.getGivenName()).isNull();
    assertThat(entity.getSurname()).isNull();
    assertThat(entity.getEnrolledSince()).isNotNull();
    assertThat(entity.getOfferCategoryPreference()).isNull();

    assertThat(entity.getEnrolledPrograms())
      .isNotNull()
      .isEmpty();
  }

  private MemberEntity buildEntity() {
    Set<ProgramEntity> programEntities = new HashSet<>();
    programEntities.add(
      ProgramEntity.builder()
        .programId("FF")
        .programName("Frequent Flyer")
        .programDescription("Frequent Flyer program")
        .build()
    );

    return MemberEntity.builder()
      .memberId(112233L)
      .accountStatus(AccountStatus.ACTIVE)
      .givenName("Fred")
      .surname("Flintstone")
      .enrolledSince(LocalDate.of(2017, 07, 26))
      .offerCategoryPreference(OfferCategory.NATURE)
      .enrolledPrograms(programEntities)
      .build();
  }

  private Member buildDto() {
    Set<Program> programs = new HashSet<>();
    programs.add(Program.builder()
      .programId("FF")
      .marketingName("Frequent Flyer")
      .summaryDescription("Frequent Flyer program")
      .build());

    return Member.builder()
      .memberId(112233L)
      .accountStatus(AccountStatus.ACTIVE)
      .firstName("Fred")
      .lastName("Flintstone")
      .memberSince(LocalDate.of(2017, 07, 26))
      .offerCategoryPreference(OfferCategory.NATURE)
      .enrolledPrograms(programs)
      .build();
  }

}
