package owuor91.io.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import owuor91.io.transactions.model.User;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WalletDto {
  private UUID walletId;

  @JsonIgnore
  private User user;

  private Double balance;

  private UUID userId;
}
