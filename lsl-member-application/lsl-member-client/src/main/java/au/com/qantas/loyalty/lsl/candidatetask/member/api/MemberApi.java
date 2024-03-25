package au.com.qantas.loyalty.lsl.candidatetask.member.api;

import static au.com.qantas.loyalty.lsl.candidatetask.member.OpenApi.MEMBER_API_TAG;

import au.com.qantas.loyalty.lsl.candidatetask.api.DefaultApiErrorResponses;
import au.com.qantas.loyalty.lsl.candidatetask.member.OpenApi;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = MEMBER_API_TAG)
@DefaultApiErrorResponses
public interface MemberApi {

  @PostMapping(value = OpenApi.MEMBER_URL)
  @Operation(summary = "Create a member",
      description = "This API returns the newly created member's profile details such as name, statuses and enrolled programs.")
  @ApiResponse(
      responseCode = "200",
      description = "The response payload contains the member's profile details.",
      content = @Content(schema = @Schema(implementation = Member.class)))
  Member createMember(@RequestBody final Member member);

  @GetMapping(value = OpenApi.MEMBER_MEMBER_ID_URL)
  @Operation(summary = "Retrieve the member profile details by member-id",
      description = "This API returns the member profile details such as name, statuses and enrolled programs.")
  @ApiResponse(
      responseCode = "200",
      description = "The response payload contains the member's profile details.",
      content = @Content(schema = @Schema(implementation = Member.class)))
  Member getMember(
      @PathVariable
      @Parameter(name = "memberId", example = "110021") final Long memberId);

}
