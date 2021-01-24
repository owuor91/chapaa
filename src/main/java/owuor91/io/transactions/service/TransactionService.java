package owuor91.io.transactions.service;

import java.util.List;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.exceptions.InsufficientBalanceException;
import owuor91.io.transactions.exceptions.UserNotFoundException;

public interface TransactionService {
  TransactionDto postTransaction(TransactionDto transactionDto) throws UserNotFoundException,
      InsufficientBalanceException;

  List<TransactionDto> listTransactionsByPhoneNumber(String phoneNumber);
}
