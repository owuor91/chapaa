package owuor91.io.transactions.exceptions;

public class InsufficientBalanceException extends ValidationException{
  public InsufficientBalanceException(String message) {
    super(message);
  }
}
