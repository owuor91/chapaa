package owuor91.io.transactions.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Wallet {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "wallet_id")
  private UUID walletId;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "balance", nullable = false)
  private Double balance;
}
