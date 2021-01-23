package owuor91.io.transactions.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "transaction_id")
  private UUID transactionId;

  @Column(name = "sender", nullable = false)
  private String sender;

  @Column(name = "receiver", nullable = false)
  private String receiver;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "timestamp", nullable = false)
  private Timestamp timestamp;

  @Column(name = "transaction_code", nullable = false)
  private String transactionCode;
}
