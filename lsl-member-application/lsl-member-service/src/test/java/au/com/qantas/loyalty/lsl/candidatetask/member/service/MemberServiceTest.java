package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.client.OfferClient;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.AddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressPK;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.QMemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.AddressCategory;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.CountryRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberAddressRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.model.OfferCategory;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  private static final Long MEMBER_ID = 112233L;

  private static final String PROGRAM_ID = "FF";

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private ProgramRepository programRepository;

  @Mock
  private MemberAddressRepository memberAddressRepository;

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private OfferClient offerClient;

  private MemberService memberService;


  @BeforeEach
  void beforeEach() {
    memberService = new MemberService(
      memberRepository,
      programRepository,
      memberAddressRepository,
      countryRepository,
      offerClient,
      new MemberConverter(new ProgramConverter()),
      new OfferConverter(),
      new MemberAddressConverter()
    );
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

    final List<ProgramEntity> programs = List.of(ProgramEntity.builder()
      .programId(PROGRAM_ID)
      .programName("Frequent Flyer")
      .programDescription("Frequent Flyer program")
      .build());

    final List<MemberAddressEntity> addresses = List.of(MemberAddressEntity.builder()
      .id(MemberAddressPK.builder()
        .address(AddressEntity.builder()
          .addressId(1L)
          .address1("Qantas")
          .address2("10 Bourke road")
          .city("Mascot")
          .postcode("2020")
          .state("NSW")
          .country(CountryEntity.builder()
            .countryId(36L)
            .countryCode1("AU")
            .countryCode2("AUS")
            .countryName("Australia")
            .build())
          .build())
        .member(memberEntity)
        .category(AddressCategory.RESIDENTIAL)
        .build())
      .build());

    when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(memberEntity));
    when(offerClient.getOffers(memberEntity.getOfferCategoryPreference())).thenReturn(offers);
    when(programRepository.findAllByMemberId(MEMBER_ID)).thenReturn(programs);

    BooleanExpression booleanExpression = QMemberAddressEntity.memberAddressEntity.id.member.memberId.eq(MEMBER_ID);
    when(memberAddressRepository.findAll(booleanExpression)).thenReturn(addresses);

    final Optional<Member> actual = memberService.getMember(MEMBER_ID);

    assertThat(actual)
      .isNotNull()
      .isPresent();

    final Member member = actual.get();

    assertThat(member.getMemberId()).isEqualTo(MEMBER_ID);
    assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
    assertThat(member.getAddresses())
      .isNotNull()
      .hasSize(1);

    assertThat(member.getAddresses().get(0).getCountry()).isEqualTo("Australia");
    assertThat(member.getAddresses().get(0).getCategory()).isEqualTo(AddressCategory.RESIDENTIAL);
  }

}
