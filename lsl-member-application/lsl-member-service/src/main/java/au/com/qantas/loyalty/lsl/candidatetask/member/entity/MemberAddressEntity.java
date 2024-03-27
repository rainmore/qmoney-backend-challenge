package au.com.qantas.loyalty.lsl.candidatetask.member.entity;

import au.com.qantas.loyalty.lsl.candidatetask.member.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_ADDRESS")
public class MemberAddressEntity {

  @EmbeddedId
  @NotNull
  private MemberAddressPK id;

}
