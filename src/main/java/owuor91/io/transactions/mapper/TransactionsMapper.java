package owuor91.io.transactions.mapper;

import org.springframework.stereotype.Component;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.model.Transaction;
import owuor91.io.transactions.model.TransactionType;

@Component
public class TransactionsMapper {
  public Transaction toTransaction(TransactionDto transactionDto) {
    return Transaction.builder()
        .transactionId(transactionDto.getTransactionId())
        .amount(transactionDto.getAmount())
        .sender(transactionDto.getSender())
        .receiver(transactionDto.getReceiver())
        .timestamp(transactionDto.getTimestamp())
        .transactionCode(transactionDto.getTransactionCode())
        .transactionType(TransactionType.getNameByValue(transactionDto.getTransactionType()))
        .build();
  }

  public TransactionDto toTransactionDto(Transaction transaction) {
    return TransactionDto.builder()
        .transactionId(transaction.getTransactionId())
        .amount(transaction.getAmount())
        .sender(transaction.getSender())
        .receiver(transaction.getReceiver())
        .timestamp(transaction.getTimestamp())
        .transactionCode(transaction.getTransactionCode())
        .transactionType(TransactionType.valueOf(transaction.getTransactionType()).getValue())
        .build();
  }
}
