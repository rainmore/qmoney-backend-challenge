package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.member.client.OfferClient;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final ProgramRepository programRepository;

  private final OfferClient offerClient;

  @Autowired
  public MemberService(
      final MemberRepository memberRepository,
      final ProgramRepository programRepository,
      final OfferClient offerClient) {

    this.memberRepository = memberRepository;
    this.programRepository = programRepository;
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

    final ProgramEntity programToEnrolMemberIn = programRepository.findDefaultProgram();
    memberEntity.setEnrolledPrograms(Set.of(programToEnrolMemberIn));

    final MemberEntity savedMember = memberRepository.save(memberEntity);

    log.info("Created a member with id '{}'", savedMember.getMemberId());

    final Set<Program> enrolledPrograms = memberEntity.getEnrolledPrograms().stream().map(programEntity ->
      Program.builder()
        .programId(programEntity.getProgramId())
        .marketingName(programEntity.getProgramName())
        .summaryDescription(programEntity.getProgramDescription())
        .build()
    ).collect(Collectors.toSet());

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

  public Member updateMember(Member member) {
    final Optional<MemberEntity> memberEntity = memberRepository.findById(member.getMemberId());

    final MemberEntity entity;
    if (memberEntity.isPresent()) {
      entity = memberEntity.get();

      entity.setSurname(member.getLastName());
      entity.setGivenName(member.getFirstName());
      entity.setAccountStatus(member.getAccountStatus());
      entity.setEnrolledSince(member.getMemberSince());
      entity.setOfferCategoryPreference(member.getOfferCategoryPreference());

      Set<ProgramEntity> programEntities = new HashSet<>();
      if (member.getEnrolledPrograms() != null) {
        member.getEnrolledPrograms().forEach(program -> programEntities.add(
          ProgramEntity.builder()
            .programId(program.getProgramId())
            .programName(program.getMarketingName())
            .programDescription(program.getSummaryDescription())
            .build()
        ));
      }
      entity.getEnrolledPrograms().clear();
      entity.getEnrolledPrograms().addAll(programEntities);
      memberRepository.save(entity);
      final Set<Program> enrolledPrograms = entity.getEnrolledPrograms().stream().map(programEntity ->
        Program.builder()
          .programId(programEntity.getProgramId())
          .marketingName(programEntity.getProgramName())
          .summaryDescription(programEntity.getProgramDescription())
          .build()
      ).collect(Collectors.toSet());

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

    return member;
  }

  public Optional<Member> getMember(final Long memberId) {

    log.info("Finding a member by id '{}'", memberId);

    final Optional<MemberEntity> optionalFoundMemberEntity = memberRepository.findById(memberId);

    if (optionalFoundMemberEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a member by id '{}'", memberId);

    final MemberEntity foundMemberEntity = optionalFoundMemberEntity.get();

    final Set<Program> enrolledPrograms = new HashSet<>();

    programRepository.findAllByMemberId(foundMemberEntity.getMemberId()).forEach(p ->
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
