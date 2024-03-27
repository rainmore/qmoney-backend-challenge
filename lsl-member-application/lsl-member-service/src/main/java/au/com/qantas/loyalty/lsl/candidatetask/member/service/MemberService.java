package au.com.qantas.loyalty.lsl.candidatetask.member.service;

import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.member.client.OfferClient;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.CountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.MemberEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.ProgramEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.QCountryEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.entity.QMemberAddressEntity;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.CountryRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberAddressRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.MemberRepository;
import au.com.qantas.loyalty.lsl.candidatetask.member.repository.ProgramRepository;
import au.com.qantas.loyalty.lsl.candidatetask.offers.model.Offer;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final ProgramRepository programRepository;

  private final MemberAddressRepository memberAddressRepository;

  private final CountryRepository countryRepository;

  private final OfferClient offerClient;

  private final MemberConverter memberConverter;

  private final OfferConverter offerConverter;

  private final MemberAddressConverter memberAddressConverter;

  @Autowired
  public MemberService(
    final MemberRepository memberRepository,
    final ProgramRepository programRepository,
    final MemberAddressRepository memberAddressRepository,
    final CountryRepository countryRepository,
    final OfferClient offerClient,
    final MemberConverter memberConverter,
    final OfferConverter offerConverter,
    final MemberAddressConverter memberAddressConverter) {

    this.memberRepository = memberRepository;
    this.programRepository = programRepository;
    this.memberAddressRepository = memberAddressRepository;
    this.countryRepository = countryRepository;
    this.offerClient = offerClient;

    this.memberConverter = memberConverter;
    this.offerConverter = offerConverter;
    this.memberAddressConverter = memberAddressConverter;
  }

  public Member createMember(final Member member) {

    log.info("Creating a member");

    final MemberEntity memberEntity = memberConverter.convertToEntity(member);

    final ProgramEntity programToEnrolMemberIn = programRepository.findDefaultProgram();
    memberEntity.setEnrolledPrograms(Set.of(programToEnrolMemberIn));

    final MemberEntity savedMember = memberRepository.save(memberEntity);
    saveMemberAddresses(memberEntity, member.getAddresses());

    log.info("Created a member with id '{}'", savedMember.getMemberId());

    return memberConverter.convertFromEntity(memberEntity);
  }

  public Member updateMember(Member member) {
    final MemberEntity memberEntity = memberRepository.findById(member.getMemberId())
      .orElseThrow(() ->
        new ResourceNotFoundException(String.format("No member exists with memberId=%d", member.getMemberId())));

    memberConverter.copyDtoToEntity(member, memberEntity);
    memberRepository.save(memberEntity);
    saveMemberAddresses(memberEntity, member.getAddresses());

    return memberConverter.convertFromEntity(memberEntity);
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

    final List<Offer> offers = offerClient.getOffers(member.getOfferCategoryPreference());
    final List<Member.Offer> memberOffers = offers.stream()
      .map(offerConverter::convertFromEntity)
      .toList();

    member.setOffers(memberOffers);

    List<Member.Address> memberAddresses = findMemberAddressEntitiesBy(foundMemberEntity)
      .map(memberAddressConverter::convertFromEntity).toList();
    member.setAddresses(memberAddresses);

    return Optional.of(member);
  }

  private void saveMemberAddresses(MemberEntity entity, List<Member.Address> addresses) {
    final List<MemberAddressEntity> memberAddressEntities = new ArrayList<>(findMemberAddressEntitiesBy(entity).toList());

    if (addresses == null || addresses.isEmpty()) {
      memberAddressRepository.deleteAllInBatch(memberAddressEntities);
      return;
    }

    Set<Long> addressIds = addresses.stream()
      .map(Member.Address::getId)
      .filter(Objects::nonNull)
      .collect(Collectors.toSet());

    List<MemberAddressEntity> memberAddressEntitiesToRemove = memberAddressEntities.stream().filter(id -> !addressIds.contains(id.getId().getAddress().getAddressId())).toList();

    if (!memberAddressEntitiesToRemove.isEmpty()) {
      memberAddressRepository.deleteAllInBatch(memberAddressEntitiesToRemove);
    }

    List<MemberAddressEntity> memberAddressEntitiesToAdd = new ArrayList<>();
    addresses.forEach(address -> {
      MemberAddressEntity memberAddressEntity = memberAddressConverter.convertToEntity(address);
      BooleanExpression countryNameCriteria = QCountryEntity.countryEntity.countryName.eq(address.getCountry());
      CountryEntity countryEntity = countryRepository.findOne(countryNameCriteria)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("No country exists with name=%s", address.getCountry())));
      memberAddressEntity.getId().getAddress().setCountry(countryEntity);
      memberAddressEntity.getId().setMember(entity);
      memberAddressEntitiesToAdd.add(memberAddressEntity);
    });

    memberAddressRepository.saveAll(memberAddressEntitiesToAdd);
  }

  private Stream<MemberAddressEntity> findMemberAddressEntitiesBy(MemberEntity member) {
    BooleanExpression memberAddressCriteria = QMemberAddressEntity.memberAddressEntity.id.member.memberId.eq(member.getMemberId());
    return StreamSupport.stream(memberAddressRepository.findAll(memberAddressCriteria).spliterator(), false);
  }

}
