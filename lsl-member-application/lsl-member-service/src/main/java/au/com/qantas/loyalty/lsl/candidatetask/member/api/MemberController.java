package au.com.qantas.loyalty.lsl.candidatetask.member.api;

import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.service.MemberService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController implements MemberApi {

  private final MemberService memberService;

  @Autowired
  MemberController(final MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public Member createMember(final Member member) {
    return memberService.createMember(member);
  }

  @Override
  public Member getMember(final Long memberId) {

    final Optional<Member> foundMember = memberService.getMember(memberId);

    if (foundMember.isEmpty()) {
      throw new ResourceNotFoundException("No member exists with memberId=" + memberId);
    }

    return foundMember.get();
  }

}
