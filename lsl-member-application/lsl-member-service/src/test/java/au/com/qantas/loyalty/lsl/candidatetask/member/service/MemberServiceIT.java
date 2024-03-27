package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.ApplicationIT;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.QMemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.AddressCategory;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberAddressRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberServiceIT extends ApplicationIT {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberAddressRepository memberAddressRepository;

  @Autowired
  private MemberService memberService;

  @Test
  void testCreateMember_willSavePrograms() {

    Member member = Member.builder()
      .accountStatus(AccountStatus.ACTIVE)
      .firstName("Test")
      .lastName("Test")
      .memberSince(LocalDate.of(2017, 7, 26))
      .offerCategoryPreference(OfferCategory.NATURE)
      .build();

    TransactionStatus status = new SimpleTransactionStatus();

    Member savedMember = ((TransactionCallback<Member>) status1 -> memberService.createMember(member))
      .doInTransaction(status);

    assertThat(savedMember.getMemberId()).isNotNull();
    assertThat(savedMember.getEnrolledPrograms())
      .isNotNull()
      .hasSize(1);
    assertThat(savedMember.getEnrolledPrograms()).isNotNull();

    Set<String> programIds = savedMember.getEnrolledPrograms().stream().map(Program::getProgramId).collect(Collectors.toSet());

    assertThat(programIds)
      .isNotEmpty()
      .hasSize(1)
      .containsAll(Set.of("FF"));


    memberRepository.deleteById(savedMember.getMemberId());
  }

  @Test
  void testCreateMember_willSaveAddresses() {

    Member member = Member.builder()
      .accountStatus(AccountStatus.ACTIVE)
      .firstName("Test")
      .lastName("Test")
      .memberSince(LocalDate.of(2017, 7, 26))
      .offerCategoryPreference(OfferCategory.NATURE)
      .addresses(buildMemberAddress())
      .build();

    TransactionStatus status = new SimpleTransactionStatus();

    Member savedMember = ((TransactionCallback<Member>) status1 -> memberService.createMember(member))
      .doInTransaction(status);

    List<MemberAddressEntity> memberAddressEntities = ((TransactionCallback<List<MemberAddressEntity>>) status1 -> {
      List<MemberAddressEntity> entities = findMemberAddressEntitiesBy(savedMember).toList();
      // to avoid lazy loading
      entities.forEach(entity -> {
        entity.getId().getAddress().getAddressId();
        entity.getId().getAddress().getCountry().getCountryId();
      });
      return entities;
    }).doInTransaction(status);

    assertThat(memberAddressEntities)
      .isNotNull()
      .hasSize(2);

    assertThat(memberAddressEntities.stream().map(entity -> entity.getId().getCategory()).collect(Collectors.toSet()))
      .hasSize(2)
      .containsAll(Set.of(AddressCategory.RESIDENTIAL, AddressCategory.POSTAL));

    assertThat(memberAddressEntities.stream().map(entity -> entity.getId().getAddress().getAddressId()).collect(Collectors.toSet()))
      .hasSize(2)
      .containsAll(Set.of(1L, 2L));

    assertThat(memberAddressEntities.stream().map(entity -> entity.getId().getAddress().getCountry().getCountryId()).collect(Collectors.toSet()))
      .hasSize(1)
      .containsAll(Set.of(36L));

    memberRepository.deleteById(savedMember.getMemberId());
  }

  @Test
  void testUpdateMember_willSavePrograms() {

    Member member = Member.builder()
      .accountStatus(AccountStatus.ACTIVE)
      .firstName("Test")
      .lastName("Test")
      .memberSince(LocalDate.of(2017, 7, 26))
      .offerCategoryPreference(OfferCategory.NATURE)
      .addresses(buildMemberAddress())
      .build();

    TransactionStatus status = new SimpleTransactionStatus();

    Member savedMember = ((TransactionCallback<Member>) status1 -> memberService.createMember(member))
      .doInTransaction(status);

    savedMember.setAddresses(List.of(buildMemberAddress().get(0)));

    Member updatedMember = ((TransactionCallback<Member>) status1 -> memberService.updateMember(savedMember))
      .doInTransaction(status);

    List<MemberAddressEntity> memberAddressEntities = ((TransactionCallback<List<MemberAddressEntity>>) status1 -> {
      List<MemberAddressEntity> entities = findMemberAddressEntitiesBy(updatedMember).toList();
      // to avoid lazy loading
      entities.forEach(entity -> {
        entity.getId().getAddress().getAddressId();
        entity.getId().getAddress().getCountry().getCountryId();
      });
      return entities;
    }).doInTransaction(status);

    assertThat(memberAddressEntities)
      .isNotNull()
      .hasSize(1);

    assertThat(memberAddressEntities.stream().map(entity -> entity.getId().getCategory()).collect(Collectors.toSet()))
      .hasSize(1)
      .containsAll(Set.of(AddressCategory.RESIDENTIAL));

    assertThat(memberAddressEntities.stream().map(entity -> entity.getId().getAddress().getAddressId()).collect(Collectors.toSet()))
      .hasSize(1)
      .containsAll(Set.of(1L));

    assertThat(memberAddressEntities.stream().map(entity -> entity.getId().getAddress().getCountry().getCountryId()).collect(Collectors.toSet()))
      .hasSize(1)
      .containsAll(Set.of(36L));

    memberRepository.deleteById(savedMember.getMemberId());
  }

  private List<Member.Address> buildMemberAddress() {
    return List.of(Member.Address.builder()
      .id(1L)
      .address1("Qantas")
      .address2("10 Bourke road")
      .city("Mascot")
      .postcode("2020")
      .state("NSW")
      .country("Australia")
      .category(AddressCategory.RESIDENTIAL)
      .build(),
      Member.Address.builder()
        .id(2L)
        .address1("Sydney Opera House")
        .address2("Bennelong Point")
        .city("Sydney")
        .postcode("2000")
        .state("NSW")
        .country("Australia")
        .category(AddressCategory.POSTAL)
        .build());
  }

  private Stream<MemberAddressEntity> findMemberAddressEntitiesBy(Member member) {
    BooleanExpression memberAddressCriteria = QMemberAddressEntity.memberAddressEntity.id.member.memberId.eq(member.getMemberId());
    return StreamSupport.stream(memberAddressRepository.findAll(memberAddressCriteria).spliterator(), false);
  }
}
