package owuor91.io.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

  private int transactionType;

  @JsonIgnore
  private String pin;
}
