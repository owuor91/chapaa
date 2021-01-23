package owuor91.io.transactions.exceptions;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
  private Boolean errors;
  private String message;
  private int statusCode;
  private List<String> details;
}
