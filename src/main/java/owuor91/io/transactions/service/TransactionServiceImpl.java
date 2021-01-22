package owuor91.io.transactions.service;


import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.mapper.TransactionsMapper;
import owuor91.io.transactions.model.Transaction;
import owuor91.io.transactions.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{
  @Autowired TransactionRepository transactionRepository;
  @Autowired TransactionsMapper transactionsMapper;

  @Override public TransactionDto postTransaction(TransactionDto transactionDto) {
    transactionDto.setTransactionCode(generateRandomCode());
    transactionDto.setTimestamp(Timestamp.from(Instant.now()));
    Transaction response = transactionRepository.save(transactionsMapper.toTransaction(transactionDto));
    return transactionsMapper.toTransactionDto(response);
  }

  private String generateRandomCode() {
    String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    SecureRandom secureRandom = new SecureRandom();
    StringBuilder stringBuilder = new StringBuilder(6);
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(source.charAt(secureRandom.nextInt(source.length())));
    }
    return stringBuilder.toString();
  }
}
