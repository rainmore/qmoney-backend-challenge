package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import au.com.qantas.loyalty.lsl.candidatetask.member.client.OfferClient;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  static final Long MEMBER_ID = 112233L;

  static final String PROGRAM_ID = "FF";

  @Mock
  MemberRepository memberRepository;

  @Mock
  ProgramRepository programRepository;

  @Mock
  MemberProgramRepository memberProgramRepository;

  @Mock
  OfferClient offerClient;

  MemberService memberService;

  @BeforeEach
  void beforeEach() {
    memberService = new MemberService(
        memberRepository,
        programRepository,
        memberProgramRepository,
        offerClient);
  }

  @Test
  void getMember_succeffullyGetsMemberEntityAndConvertsToMemberModel() {

    final MemberEntity memberEntity = MemberEntity.builder()
        .memberId(MEMBER_ID)
        .accountStatus(AccountStatus.ACTIVE)
        .givenName("Fred")
        .surname("Flintstone")
        .enrolledSince(LocalDate.of(2017, 07, 26))
        .offerCategoryPreference(OfferCategory.NATURE)
        .build();

    final List<Offer> offers = List.of(Offer.builder()
        .id(3344L)
        .name("Autumn Leaves")
        .description("See the autumn leaves of Canada")
        .build());

    final List<MemberProgramEntity> memberProgramEntities = List.of(MemberProgramEntity.builder()
        .memberId(MEMBER_ID)
        .programId(PROGRAM_ID)
        .build());

    final List<ProgramEntity> programs = List.of(ProgramEntity.builder()
            .programId(PROGRAM_ID)
            .programName("Frequent Flyer")
            .programDescription("Frequent Flyer program")
        .build());


    when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(memberEntity));
    when(offerClient.getOffers()).thenReturn(offers);
    when(memberProgramRepository.findAllByMemberId(MEMBER_ID)).thenReturn(memberProgramEntities);
    when(programRepository.findAllById(List.of(PROGRAM_ID))).thenReturn(programs);

    final Optional<Member> actual = memberService.getMember(MEMBER_ID);

    assertThat(actual)
        .isNotNull()
            .isPresent();

    final Member member = actual.get();

    assertThat(member.getMemberId()).isEqualTo(MEMBER_ID);
    assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);

  }

}
