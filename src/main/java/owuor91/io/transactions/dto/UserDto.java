package owuor91.io.transactions.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private UUID userId;

  private String name;

  private String phoneNumber;

  private String pin;
}
