package owuor91.io.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import owuor91.io.transactions.model.Wallet;

@Builder
@Data
@AllArgsConstructor
public class TransactionDto {
  private UUID transactionId;

  private String sender;

  private String receiver;

  private Double amount;

  private Timestamp timestamp;

  private String transactionCode;
}
