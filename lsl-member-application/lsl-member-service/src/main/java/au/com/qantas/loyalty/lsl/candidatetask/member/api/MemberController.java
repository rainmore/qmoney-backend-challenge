package au.com.qantas.loyalty.lsl.candidatetask.member.api;

import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.service.MemberService;
import au.com.qantas.loyalty.lsl.candidatetask.member.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MemberController implements MemberApi {

  private final MemberService memberService;

  private final ProgramService programService;

  @Autowired
  MemberController(final MemberService memberService, final ProgramService programService) {
    this.memberService = memberService;
    this.programService = programService;
  }

  @Override
  public Member createMember(final Member member) {
    return memberService.createMember(member);
  }

  @Override
  public Member getMember(final Long memberId) {
    return getMemberBy(memberId);
  }

  @Override
  public Member enrollPrograms(final Long memberId, final Set<String> programIds) {
    if (programIds == null || programIds.isEmpty()) {
      throw new ResourceNotFoundException("Please provide programIds!");
    }

    Member member = getMemberBy(memberId);

    List<Program> programs = programService.getProgramsBy(programIds);

    if (programs.isEmpty()) {
      throw new ResourceNotFoundException(
        String.format("No programs exists in the provided programIds [%s]",
              String.join(", ", programIds)));
    }
    Set<String> foundProgramIds = programs.stream().map(Program::getProgramId).collect(Collectors.toSet());

    if (programIds.size() != foundProgramIds.size() || !foundProgramIds.containsAll(programIds)) {
      throw new ResourceNotFoundException(
        String.format("Invalid programs are provided [%s]",
          String.join(", ", programIds)));
    }

    member.setEnrolledPrograms(new HashSet<>(programs));

    return memberService.updateMember(member);
  }

  private Member getMemberBy(final Long memberId) throws ResourceNotFoundException {
    final Optional<Member> foundMember = memberService.getMember(memberId);

    if (foundMember.isEmpty()) {
      throw new ResourceNotFoundException(String.format("No member exists with memberId=%d", memberId));
    }

    return foundMember.get();
  }
}
