package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.member.client.OfferClient;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final ProgramRepository programRepository;

  private final OfferClient offerClient;

  private final MemberConverter memberConverter;

  private final OfferConverter offerConverter;

  @Autowired
  public MemberService(
      final MemberRepository memberRepository,
      final ProgramRepository programRepository,
      final OfferClient offerClient,
      final MemberConverter memberConverter,
      final OfferConverter offerConverter) {

    this.memberRepository = memberRepository;
    this.programRepository = programRepository;
    this.offerClient = offerClient;

    this.memberConverter = memberConverter;
    this.offerConverter = offerConverter;
  }

  public Member createMember(final Member member) {

    log.info("Creating a member");

    final MemberEntity memberEntity = memberConverter.convertToEntity(member);

    final ProgramEntity programToEnrolMemberIn = programRepository.findDefaultProgram();
    memberEntity.setEnrolledPrograms(Set.of(programToEnrolMemberIn));

    final MemberEntity savedMember = memberRepository.save(memberEntity);

    log.info("Created a member with id '{}'", savedMember.getMemberId());

    return memberConverter.convertFromEntity(memberEntity);
  }

  public Member updateMember(Member member) {
    final MemberEntity entity = memberRepository.findById(member.getMemberId())
      .orElseThrow(() ->
        new ResourceNotFoundException(String.format("No member exists with memberId=%d", member.getMemberId())));

    memberConverter.copyDtoToEntity(member, entity);
    memberRepository.save(entity);

    return memberConverter.convertFromEntity(entity);
  }

  public Optional<Member> getMember(final Long memberId) {

    log.info("Finding a member by id '{}'", memberId);

    final Optional<MemberEntity> optionalFoundMemberEntity = memberRepository.findById(memberId);

    if (optionalFoundMemberEntity.isEmpty()) {
      return Optional.empty();
    }

    log.info("Found a member by id '{}'", memberId);

    final MemberEntity foundMemberEntity = optionalFoundMemberEntity.get();
    final Set<ProgramEntity> enrolledPrograms = new HashSet<>();
    programRepository.findAllByMemberId(foundMemberEntity.getMemberId()).forEach(enrolledPrograms::add);

    foundMemberEntity.setEnrolledPrograms(enrolledPrograms);

    Member member = memberConverter.convertFromEntity(foundMemberEntity);

    final List<Offer> offers = offerClient.getOffers();
    final List<Member.Offer> memberOffers = offers.stream()
        .map(offerConverter::convertFromEntity)
        .toList();

    member.setOffers(memberOffers);

    return Optional.of(member);
  }
}
