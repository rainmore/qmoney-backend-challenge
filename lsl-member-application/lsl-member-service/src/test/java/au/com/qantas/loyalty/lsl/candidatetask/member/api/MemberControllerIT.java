package au.com.qantas.loyalty.lsl.candidatetask.member.api;

import au.com.qantas.loyalty.lsl.candidatetask.api.InternalServerException;
import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.member.OpenApi;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.AddressCategory;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.service.MemberService;
import au.com.qantas.loyalty.lsl.candidatetask.member.service.ProgramService;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerIT extends AbstractControllerIT {

  private static final Long MEMBER_ID = 112233L;

  private MockMvc mockMvc;

  @MockBean
  private MemberService memberService;

  @MockBean
  private ProgramService programService;

  @Autowired
  private WebApplicationContext wac;

  @BeforeEach
  void beforeEach() {
    this.mockMvc = MockMvcBuilders
      .webAppContextSetup(this.wac)
      .build();
  }

  @Test
  void createServiceRequest_shouldReturnHttpStatus200() throws Exception {

    final Member expected = buildMember();

    when(memberService.getMember(MEMBER_ID)).thenReturn(Optional.of(expected));

    mockMvc.perform(
        get(OpenApi.MEMBER_MEMBER_ID_URL, MEMBER_ID)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.memberId", equalTo(MEMBER_ID.intValue())))
      .andExpect(jsonPath("$.accountStatus", is("ACTIVE")))
      .andExpect(jsonPath("$.firstName", is("Fred")))
      .andExpect(jsonPath("$.lastName", is("Flintstone")))
      .andExpect(jsonPath("$.enrolledPrograms[0].programId", is("FF")))
      .andExpect(jsonPath("$.enrolledPrograms[0].marketingName", is("Frequent Flyer")));
  }

  @Test
  void createServiceRequest_shouldReturnHttpStatus404() throws Exception {

    final ResourceNotFoundException exception = new ResourceNotFoundException("Member not found");

    when(memberService.getMember(MEMBER_ID)).thenThrow(exception);

    mockMvc.perform(
        get(OpenApi.MEMBER_MEMBER_ID_URL, MEMBER_ID)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void createServiceRequest_shouldReturnHttpStatus500() throws Exception {

    final InternalServerException exception = new InternalServerException("Internal System Error");

    when(memberService.getMember(MEMBER_ID)).thenThrow(exception);

    mockMvc.perform(
        get(OpenApi.MEMBER_MEMBER_ID_URL, MEMBER_ID)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isInternalServerError());
  }

  @Test
  void enrollProgramsServiceRequest_withValidProgramIds_shouldReturnHttpStatus200() throws Exception {
    final List<Program> programs = buildPrograms();
    final Member existingMember = buildMember();
    final Member expected = buildMember();
    expected.setEnrolledPrograms(new HashSet<>(programs));

    final Set<String> submittedProgramIds = programs.stream().map(Program::getProgramId).collect(Collectors.toSet());

    when(memberService.getMember(MEMBER_ID)).thenReturn(Optional.of(existingMember));
    when(programService.getProgramsBy(submittedProgramIds)).thenReturn(programs);
    when(memberService.updateMember(expected)).thenReturn(expected);

    mockMvc.perform(
        put(OpenApi.MEMBER_ENROLL_PROGRAM_URL, MEMBER_ID)
          .content(objectMapper.writeValueAsString(submittedProgramIds))
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.memberId", equalTo(MEMBER_ID.intValue())))
      .andExpect(jsonPath("$.accountStatus", is("ACTIVE")))
      .andExpect(jsonPath("$.firstName", is("Fred")))
      .andExpect(jsonPath("$.lastName", is("Flintstone")))
      .andExpect(jsonPath("$.enrolledPrograms[0].programId", is("PL")))
      .andExpect(jsonPath("$.enrolledPrograms[0].marketingName", is("Premiere Lounge")))
      .andExpect(jsonPath("$.enrolledPrograms[2].programId", is("FF")))
      .andExpect(jsonPath("$.enrolledPrograms[2].marketingName", is("Frequent Flyer")))
      .andExpect(jsonPath("$.enrolledPrograms[1].programId", is("ER")))
      .andExpect(jsonPath("$.addresses[0].id", is(1)))
      .andExpect(jsonPath("$.addresses[0].address1", is("Qantas")))
      .andExpect(jsonPath("$.addresses[0].address2", is("10 Bourke road")))
      .andExpect(jsonPath("$.addresses[0].city", is("Mascot")))
      .andExpect(jsonPath("$.addresses[0].postcode", is("2020")))
      .andExpect(jsonPath("$.addresses[0].state", is("NSW")))
      .andExpect(jsonPath("$.addresses[0].country", is("Australia")))
      .andExpect(jsonPath("$.addresses[0].category", is("RESIDENTIAL")));
  }

  @Test
  void enrollProgramsServiceRequest_withInvalidProgramIds_shouldReturnHttpStatus404() throws Exception {
    final Set<String> submittedProgramIds = Set.of("FF", "LL");
    final List<Program> programs = buildPrograms().stream().filter(
      program -> submittedProgramIds.contains(program.getProgramId())).toList();
    final Member existingMember = buildMember();
    final Member expected = buildMember();
    expected.setEnrolledPrograms(new HashSet<>(programs));

    when(memberService.getMember(MEMBER_ID)).thenReturn(Optional.of(existingMember));
    when(programService.getProgramsBy(submittedProgramIds)).thenReturn(programs);
    when(memberService.updateMember(expected)).thenReturn(expected);

    mockMvc.perform(
        put(OpenApi.MEMBER_ENROLL_PROGRAM_URL, MEMBER_ID)
          .content(objectMapper.writeValueAsString(submittedProgramIds))
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void enrollProgramsServiceRequest_withEmptyProgramIds_shouldReturnHttpStatus404() throws Exception {
    final Set<String> submittedProgramIds = new HashSet<>();
    final List<Program> programs = new ArrayList<>();
    final Member existingMember = buildMember();
    final Member expected = buildMember();
    expected.setEnrolledPrograms(new HashSet<>(programs));

    when(memberService.getMember(MEMBER_ID)).thenReturn(Optional.of(existingMember));
    when(programService.getProgramsBy(submittedProgramIds)).thenReturn(programs);
    when(memberService.updateMember(expected)).thenReturn(expected);

    mockMvc.perform(
        put(OpenApi.MEMBER_ENROLL_PROGRAM_URL, MEMBER_ID)
          .content(objectMapper.writeValueAsString(submittedProgramIds))
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void enrollProgramsServiceRequest_withInvalidMemberId_shouldReturnHttpStatus404() throws Exception {
    final List<Program> programs = buildPrograms();
    final Set<String> submittedProgramIds = programs.stream().map(Program::getProgramId).collect(Collectors.toSet());

    when(memberService.getMember(1111L)).thenReturn(Optional.empty());

    mockMvc.perform(
        put(OpenApi.MEMBER_ENROLL_PROGRAM_URL, 1111L)
          .content(objectMapper.writeValueAsString(submittedProgramIds))
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void enrollProgramsServiceRequest_shouldReturnHttpStatus500() throws Exception {
    final InternalServerException exception = new InternalServerException("Internal System Error");

    when(memberService.getMember(MEMBER_ID)).thenThrow(exception);

    mockMvc.perform(
        get(OpenApi.MEMBER_MEMBER_ID_URL, MEMBER_ID)
          .characterEncoding(StandardCharsets.UTF_8.displayName())
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isInternalServerError());
  }

  private Member buildMember() {
    return Member.builder()
      .memberId(MEMBER_ID)
      .accountStatus(AccountStatus.ACTIVE)
      .firstName("Fred")
      .lastName("Flintstone")
      .enrolledPrograms(Set.of(buildPrograms().get(0)))
      .addresses(buildMemberAddress())
      .build();
  }

  private List<Program> buildPrograms() {
    return List.of(
      Program.builder()
        .programId("FF")
        .marketingName("Frequent Flyer")
        .summaryDescription("Frequent Flyer Program")
        .build(),
      Program.builder()
        .programId("PL")
        .marketingName("Premiere Lounge")
        .summaryDescription("Our Premier Lounge program provides you access to our airport lounges")
        .build(),
      Program.builder()
        .programId("ER")
        .marketingName("Extra Rewards")
        .summaryDescription("With Extra Rewards you will earn more points for both flights and purchases")
        .build()
    );
  }

  private List<Member.Address> buildMemberAddress() {
    return List.of(Member.Address.builder()
      .id(1L)
      .address1("Qantas")
      .address2("10 Bourke road")
      .city("Mascot")
      .postcode("2020")
      .state("NSW")
      .country("Australia")
      .category(AddressCategory.RESIDENTIAL)
      .build());
  }

}
