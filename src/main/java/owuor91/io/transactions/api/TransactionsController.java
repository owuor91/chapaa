package owuor91.io.transactions.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.exceptions.Error;
import owuor91.io.transactions.exceptions.InsufficientBalanceException;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.service.TransactionService;

@RestController
public class TransactionsController extends ApiController {
  @Autowired TransactionService transactionService;

  @PostMapping(value = "/transaction", produces = "application/json")
  public ResponseEntity<TransactionDto> postTransaction(@RequestBody String payload) {
    System.out.println(payload);
    TransactionDto transactionDto = new Gson().fromJson(payload, TransactionDto.class);
    try {
      TransactionDto response = transactionService.postTransaction(transactionDto);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    catch (UserNotFoundException | InsufficientBalanceException e){
      Error error = new Error(true, e.getMessage(), 401);
      return new ResponseEntity(new Gson().toJson(error), HttpStatus.BAD_REQUEST);
    }
  }
}
