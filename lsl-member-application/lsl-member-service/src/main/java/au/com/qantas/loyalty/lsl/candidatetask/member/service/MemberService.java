package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.client.OfferClient;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {

  private static final String FREQUENT_FLYER_PROGRAM = "FF";

  private final MemberRepository memberRepository;

  private final ProgramRepository programRepository;

  private final MemberProgramRepository memberProgramRepository;

  private final OfferClient offerClient;

  @Autowired
  public MemberService(
      final MemberRepository memberRepository,
      final ProgramRepository programRepository,
      final MemberProgramRepository memberProgramRepository,
      final OfferClient offerClient) {

    this.memberRepository = memberRepository;
    this.programRepository = programRepository;
    this.memberProgramRepository = memberProgramRepository;
    this.offerClient = offerClient;
  }

  public Member createMember(final Member member) {

    log.info("Creating a member");

    final MemberEntity memberEntity = MemberEntity.builder()
        .accountStatus(AccountStatus.PENDING)
        .givenName(member.getFirstName())
        .surname(member.getLastName())
        .enrolledSince(LocalDate.now())
        .offerCategoryPreference(member.getOfferCategoryPreference())
        .build();

    final MemberEntity savedMember = memberRepository.save(memberEntity);

    log.info("Created a member with id '{}'", savedMember.getMemberId());

    final Optional<ProgramEntity> programToEnrolMemberIn = programRepository.findById(FREQUENT_FLYER_PROGRAM);

    final Set<Program> enrolledPrograms = new HashSet<>();

    if (programToEnrolMemberIn.isPresent()) {
      memberProgramRepository.save(MemberProgramEntity.builder()
          .memberId(memberEntity.getMemberId())
          .programId(programToEnrolMemberIn.get().getProgramId())
          .build());

      enrolledPrograms.add(Program.builder()
          .programId(programToEnrolMemberIn.get().getProgramId())
          .marketingName(programToEnrolMemberIn.get().getProgramName())
          .summaryDescription(programToEnrolMemberIn.get().getProgramDescription())
          .build());
    }

    return Member.builder()
        .memberId(savedMember.getMemberId())
        .accountStatus(savedMember.getAccountStatus())
        .enrolledPrograms(enrolledPrograms)
        .firstName(savedMember.getGivenName())
        .lastName(savedMember.getSurname())
        .memberSince(savedMember.getEnrolledSince())
        .offerCategoryPreference(savedMember.getOfferCategoryPreference())
        .build();
  }

  public Optional<Member> getMember(final Long memberId) {

    log.info("Finding a member by id '{}'", memberId);

    final Optional<MemberEntity> optionalFoundMemberEntity = memberRepository.findById(memberId);

    if (optionalFoundMemberEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a member by id '{}'", memberId);

    final MemberEntity foundMemberEntity = optionalFoundMemberEntity.get();

    final Set<String> programIds = new HashSet<>();

    final Iterable<MemberProgramEntity> memberProgramEntityS = memberProgramRepository.findAllByMemberId(foundMemberEntity.getMemberId());

    memberProgramEntityS.forEach(mp -> programIds.add(mp.getProgramId()));

    final Set<Program> enrolledPrograms = new HashSet<>();

    programRepository.findAllById(programIds).forEach(p ->
        enrolledPrograms.add(Program.builder()
            .programId(p.getProgramId())
            .marketingName(p.getProgramName())
            .summaryDescription(p.getProgramDescription())
            .build()));

    final List<Offer> offers = offerClient.getOffers();

    final List<Member.Offer> memberOffers = offers.stream()
        .map(o -> Member.Offer.builder()
            .id(o.getId())
            .name(o.getName())
            .description(o.getDescription())
            .build())
        .toList();

    return Optional.of(Member.builder()
        .memberId(foundMemberEntity.getMemberId())
        .accountStatus(foundMemberEntity.getAccountStatus())
        .enrolledPrograms(enrolledPrograms)
        .firstName(foundMemberEntity.getGivenName())
        .lastName(foundMemberEntity.getSurname())
        .memberSince(foundMemberEntity.getEnrolledSince())
        .offerCategoryPreference(foundMemberEntity.getOfferCategoryPreference())
        .offers(memberOffers)
        .build());
  }
}
