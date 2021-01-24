package owuor91.io.transactions.api;

import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.exceptions.InsufficientBalanceException;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.service.SmsSenderImpl;
import owuor91.io.transactions.service.TransactionService;
import owuor91.io.transactions.util.Util;

@RestController
public class TransactionsController extends ApiController {
  @Autowired TransactionService transactionService;
  @Autowired SmsSenderImpl smsSender;

  @PostMapping(value = "/transaction", produces = "application/json")
  public ResponseEntity<TransactionDto> postTransaction(@RequestBody String payload) throws
      UserNotFoundException, InsufficientBalanceException {
    System.out.println(payload);
    TransactionDto transactionDto = new Gson().fromJson(payload, TransactionDto.class);
    transactionDto.setTransactionType(1);
    TransactionDto response = transactionService.postTransaction(transactionDto);
    CompletableFuture.runAsync(() -> {
      smsSender.sendSms(smsSender.composeSenderMessage(response),
          Util.formatPhoneNumber(response.getSender()));
    });

    CompletableFuture.runAsync(() -> {
      smsSender.sendSms(smsSender.composeReceiptMessage(response),
          Util.formatPhoneNumber(response.getReceiver()));
    });
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping(value = "/transaction", produces = "application/json")
  public ResponseEntity<List<TransactionDto>> getTransactionsByPhone(
      @RequestParam String phoneNumber) {
    return new ResponseEntity<List<TransactionDto>>(
        transactionService.listTransactionsByPhoneNumber(phoneNumber), HttpStatus.OK);
  }
}
