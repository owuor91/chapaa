package owuor91.io.transactions.model;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(name = "receiver")
  private String receiver;

  @Min(value = 1, message = "Amount must be more than 0")
  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "timestamp", nullable = false)
  private Timestamp timestamp;

  @Column(name = "transaction_code", nullable = false)
  private String transactionCode;

  @NotEmpty(message = "transaction type must not be blank")
  @Column(name = "transaction_type", nullable = false)
  private String transactionType;
}
