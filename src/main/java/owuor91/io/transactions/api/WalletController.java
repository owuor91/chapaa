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
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.Error;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.service.WalletService;

@RestController
public class WalletController extends ApiController {
  @Autowired WalletService walletService;

  @PostMapping(value = "/wallet", produces = "application/json")
  public ResponseEntity createWallet(@RequestBody String payload) {
    System.out.println(payload);
    WalletDto walletDto = new Gson().fromJson(payload, WalletDto.class);
    try {
      WalletDto response = walletService.createWallet(walletDto);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
      Error error = new Error(true, e.getMessage(), 401);
      return new ResponseEntity<>(new Gson().toJson(error), HttpStatus.BAD_REQUEST);
    }
  }
}
