package au.com.qantas.loyalty.lsl.candidatetask.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Program {

  @Size(max = 8)
  @JsonProperty(index = 1)
  private String programId;

  @Size(max = 20)
  @JsonProperty(index = 2)
  private String marketingName;

  @Size(max = 200)
  @JsonProperty(index = 3)
  private String summaryDescription;
}
