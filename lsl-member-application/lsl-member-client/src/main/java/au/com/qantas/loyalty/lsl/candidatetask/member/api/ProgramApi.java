package au.com.qantas.loyalty.lsl.candidatetask.member.api;

import static au.com.qantas.loyalty.lsl.candidatetask.member.OpenApi.PROGRAM_API_TAG;

import au.com.qantas.loyalty.lsl.candidatetask.api.DefaultApiErrorResponses;
import au.com.qantas.loyalty.lsl.candidatetask.member.OpenApi;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Member;
import au.com.qantas.loyalty.lsl.candidatetask.member.model.Program;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = PROGRAM_API_TAG)
@DefaultApiErrorResponses
public interface ProgramApi {

  @GetMapping(value = OpenApi.PROGRAM_URL)
  @Operation(summary = "Retrieve all programs",
      description = "This API returns the program information such as id, name and description.")
  @ApiResponse(
      responseCode = "200",
      description = "The response payload contains the program information.",
      content = @Content(schema = @Schema(implementation = Program.class)))
  List<Program> getPrograms();

  @GetMapping(value = OpenApi.PROGRAM_PROGRAM_ID_URL)
  @Operation(summary = "Retrieve the program information by program-id",
      description = "This API returns the program information such as id, name and description.")
  @ApiResponse(
      responseCode = "200",
      description = "The response payload contains the program information.",
      content = @Content(schema = @Schema(implementation = Program.class)))
  Program getProgram(
      @PathVariable
      @Parameter(example = "FF") final String programId);

}
