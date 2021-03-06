package owuor91.io.transactions.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.exceptions.InsufficientBalanceException;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.mapper.TransactionsMapper;
import owuor91.io.transactions.model.Transaction;
import owuor91.io.transactions.model.TransactionType;
import owuor91.io.transactions.model.User;
import owuor91.io.transactions.model.Wallet;
import owuor91.io.transactions.repository.TransactionRepository;
import owuor91.io.transactions.repository.UserRepository;
import owuor91.io.transactions.repository.WalletRepository;

import static owuor91.io.transactions.util.Util.generateRandomCode;

@Service
public class TransactionServiceImpl implements TransactionService {
  @Autowired TransactionRepository transactionRepository;
  @Autowired TransactionsMapper transactionsMapper;
  @Autowired UserRepository userRepository;
  @Autowired WalletRepository walletRepository;

  @Transactional
  @Override public TransactionDto postTransaction(TransactionDto transactionDto)
      throws UserNotFoundException, InsufficientBalanceException {
    transactionDto.setTransactionCode(generateRandomCode(false));
    transactionDto.setTimestamp(Timestamp.from(Instant.now()));
    Wallet senderWallet = findWalletByPhoneNumber(transactionDto.getSender());
    Wallet receiverWallet = findWalletByPhoneNumber(transactionDto.getReceiver());
    if (senderWallet == null) {
      throw new UserNotFoundException(
          String.format("User with phone number %s doesn't exist", transactionDto.getSender()));
    }
    if (receiverWallet == null) {
      throw new UserNotFoundException(
          String.format("User with phone number %s doesn't exist", transactionDto.getReceiver()));
    }
    if (transactionDto.getAmount() > senderWallet.getBalance()) {
      throw new InsufficientBalanceException(String.format(
          "You do not have enough funds to %s KES %2.1f, Your available balance is KES %2.1f",
          TransactionType.getNameByValue(transactionDto.getTransactionType()).toLowerCase(),
          transactionDto.getAmount(), senderWallet.getBalance()));
    }
    senderWallet.setBalance(senderWallet.getBalance() - transactionDto.getAmount());
    receiverWallet.setBalance(receiverWallet.getBalance() + transactionDto.getAmount());
    walletRepository.save(senderWallet);
    walletRepository.save(receiverWallet);
    Transaction response =
        transactionRepository.save(transactionsMapper.toTransaction(transactionDto));
    return transactionsMapper.toTransactionDto(response);
  }

  private Wallet findWalletByPhoneNumber(String phoneNumber) {
    Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user.getWallet();
    }
    return null;
  }

  @Override public List<TransactionDto> listTransactionsByPhoneNumber(String phoneNumber) {
    List<Transaction> transactionList =
        transactionRepository.findBySenderOrReceiver(phoneNumber, phoneNumber);
    return transactionList.stream()
        .map(transaction -> transactionsMapper.toTransactionDto(transaction))
        .collect(Collectors.toList());
  }
}
