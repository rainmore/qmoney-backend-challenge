package au.com.qantas.loyalty.lsl.candidatetask.member.entity;

import au.com.qantas.loyalty.lsl.candidatetask.member.model.AddressCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MemberAddressPK implements Serializable {


  @ManyToOne
  @JoinColumn(name = "ADDRESS_ID", nullable = false)
  @NotNull
  private AddressEntity address;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false)
  @NotNull
  private MemberEntity member;

  @NotNull
  @Column(name = "ADDRESS_CATEGORY", nullable = false)
  @Enumerated(EnumType.STRING)
  private AddressCategory category;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemberAddressPK that = (MemberAddressPK) o;
    return Objects.equals(address, that.address) && Objects.equals(member,
      that.member) && category == that.category;
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, member, category);
  }
}
