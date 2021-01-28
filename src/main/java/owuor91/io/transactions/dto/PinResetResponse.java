package owuor91.io.transactions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PinResetResponse {
  private String message;
  private Boolean success;
  @JsonIgnore
  private String newPin;
  @JsonIgnore
  private String phoneNumber;
}
