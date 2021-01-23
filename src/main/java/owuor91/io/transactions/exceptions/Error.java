package owuor91.io.transactions.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
public class Error {
  private Boolean errors;
  private String message;
  private int statusCode;
}
