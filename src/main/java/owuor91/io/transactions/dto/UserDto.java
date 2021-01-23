package owuor91.io.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private UUID userId;

  private String name;

  private String phoneNumber;

  @JsonIgnore
  private String pin;
}
