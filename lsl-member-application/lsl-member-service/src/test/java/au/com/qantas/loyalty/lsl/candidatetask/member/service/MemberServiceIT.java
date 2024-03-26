package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.ApplicationIT;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberServiceIT extends ApplicationIT {

  @Autowired
  private MemberRepository memberRepository;

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

}
