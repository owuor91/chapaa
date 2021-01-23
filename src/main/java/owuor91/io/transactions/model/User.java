package owuor91.io.transactions.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private UUID userId;

  @NotEmpty(message = "Name must not be blank")
  @Column(name = "name", nullable = false)
  private String name;

  @NotEmpty(message = "Phone number must not be blank")
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @NotEmpty(message = "pin must not be blank")
  @Column(name = "pin", nullable = false)
  private String pin;

  @OneToOne
  @JoinColumn(name = "wallet_id")
  private Wallet wallet;
}
