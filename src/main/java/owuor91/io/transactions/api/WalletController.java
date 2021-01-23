package owuor91.io.transactions.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.BalanceDto;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.InsufficientBalanceException;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.service.SmsSenderImpl;
import owuor91.io.transactions.service.TransactionService;
import owuor91.io.transactions.service.UserService;
import owuor91.io.transactions.service.WalletService;
import owuor91.io.transactions.util.Util;

@RestController
public class WalletController extends ApiController {
  @Autowired WalletService walletService;

  @Autowired TransactionService transactionService;

  @Autowired UserService userService;

  @Autowired SmsSenderImpl smsSender;

  @PostMapping(value = "/wallet", produces = "application/json")
  public ResponseEntity<WalletDto> createWallet(@RequestBody String payload)
      throws UserNotFoundException {
    System.out.println(payload);
    WalletDto walletDto = new Gson().fromJson(payload, WalletDto.class);
    WalletDto response = walletService.createWallet(walletDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping(value = "/wallet/deposit", produces = "application/json")
  public ResponseEntity<TransactionDto> fundWallet(@RequestBody String payload)
      throws IOException, UserNotFoundException,
      InsufficientBalanceException {
    System.out.println(payload);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(payload);
    String phoneNumber = root.get("phoneNumber").textValue();
    Double amount = root.get("amount").doubleValue();
    TransactionDto transactionDto = TransactionDto.builder()
        .transactionType(2)
        .sender(getDefaultSystemWalletNumber())
        .receiver(phoneNumber)
        .amount(amount)
        .build();
    TransactionDto response = transactionService.postTransaction(transactionDto);
    CompletableFuture.runAsync(() -> {
      smsSender.sendSms(smsSender.composeDepositMessage(response),
          Util.formatPhoneNumber(phoneNumber));
    });
    return new ResponseEntity<TransactionDto>(response, HttpStatus.OK);
  }

  @PostMapping(value = "/wallet/withdraw", produces = "application/json")
  public ResponseEntity<TransactionDto> withdrawFunds(@RequestBody String payload)
      throws IOException, UserNotFoundException,
      InsufficientBalanceException {
    System.out.println(payload);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(payload);
    String phoneNumber = root.get("phoneNumber").textValue();
    Double amount = root.get("amount").doubleValue();
    TransactionDto transactionDto = TransactionDto.builder()
        .transactionType(3)
        .sender(phoneNumber)
        .receiver(getDefaultSystemWalletNumber())
        .amount(amount)
        .build();

    TransactionDto response = transactionService.postTransaction(transactionDto);
    CompletableFuture.runAsync(() -> {
      smsSender.sendSms(smsSender.composeWithdrawMessage(response),
          Util.formatPhoneNumber(phoneNumber));
    });
    return new ResponseEntity<TransactionDto>(response, HttpStatus.OK);
  }

  private String getDefaultSystemWalletNumber() throws UserNotFoundException {
    return userService.findDefaultSystemUser().getPhoneNumber();
  }

  @GetMapping(value = "/wallet/balance", produces = "application/json")
  public ResponseEntity<BalanceDto> checkBalance(@RequestParam String phoneNumber)
      throws UserNotFoundException {
    WalletDto userWallet = walletService.getWalletByPhoneNumber(phoneNumber);
    BalanceDto balanceDto =
        BalanceDto.builder()
            .phoneNumber(userWallet.getUser().getPhoneNumber())
            .balance(userWallet.getBalance())
            .timestamp(
                Timestamp.from(Instant.now()))
            .build();
    CompletableFuture.runAsync(() -> {
      smsSender.sendSms(smsSender.composeBalanceMessage(balanceDto),
          Util.formatPhoneNumber(phoneNumber));
    });
    return new ResponseEntity<BalanceDto>(balanceDto, HttpStatus.OK);
  }
}
