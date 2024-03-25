package au.com.qantas.loyalty.lsl.candidatetask.member.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.qantas.loyalty.lsl.candidatetask.api.InternalServerException;
import au.com.qantas.loyalty.lsl.candidatetask.api.ResourceNotFoundException;
import au.com.qantas.loyalty.lsl.candidatetask.member.OpenApi;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import au.com.qantas.loyalty.lsl.candidatetask.member.service.MemberService;
import au.com.qantas.loyalty.lsl.candidatetask.model.AccountStatus;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

class MemberControllerIT extends AbstractControllerIT {

  private static final Long MEMBER_ID = 112233L;

  private MockMvc mockMvc;

  @MockBean
  private MemberService memberService;

  private @Autowired
  WebApplicationContext wac;

  @BeforeEach
  void beforeEach() {

    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(this.wac)
        .build();
  }

  @Test
  void createServiceRequest_shouldReturnHttpStatus200() throws Exception {

    final Member expected = Member.builder()
        .memberId(MEMBER_ID)
        .accountStatus(AccountStatus.ACTIVE)
        .firstName("Fred")
        .lastName("Flintstone")
        .enrolledPrograms(Set.of(Program.builder()
                .programId("FF")
                .marketingName("Frequent Flyer")
                .summaryDescription("Frequent Flyer Program")
            .build()))
        .build();

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

}
