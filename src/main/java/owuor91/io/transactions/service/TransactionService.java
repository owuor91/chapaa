package owuor91.io.transactions.service;

import owuor91.io.transactions.dto.TransactionDto;

public interface TransactionService {
  TransactionDto postTransaction(TransactionDto transactionDto);
}
