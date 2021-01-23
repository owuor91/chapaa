package owuor91.io.transactions.dto;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceDto {
  public String phoneNumber;

  public Timestamp timestamp;

  public Double balance;
}
