package owuor91.io.transactions.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.InsufficientBalanceException;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.service.TransactionService;
import owuor91.io.transactions.service.UserService;
import owuor91.io.transactions.service.WalletService;

@RestController
public class WalletController extends ApiController {
  @Autowired WalletService walletService;

  @Autowired TransactionService transactionService;

  @Autowired UserService userService;

  @PostMapping(value = "/wallet", produces = "application/json")
  public ResponseEntity<WalletDto> createWallet(@RequestBody String payload)
      throws UserNotFoundException {
    System.out.println(payload);
    WalletDto walletDto = new Gson().fromJson(payload, WalletDto.class);
    WalletDto response = walletService.createWallet(walletDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping(value = "/wallet/fund", produces = "application/json")
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

    return new ResponseEntity<TransactionDto>(transactionService.postTransaction(transactionDto),
        HttpStatus.OK);
  }

  private String getDefaultSystemWalletNumber() throws UserNotFoundException {
    return userService.findDefaultSystemUser().getPhoneNumber();
  }
}
