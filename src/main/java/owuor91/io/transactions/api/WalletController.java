package owuor91.io.transactions.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.service.WalletService;

@RestController
public class WalletController extends ApiController {
  @Autowired WalletService walletService;

  @PostMapping(value = "/wallet", produces = "application/json")
  public ResponseEntity createWallet(@RequestBody String payload) throws UserNotFoundException {
    System.out.println(payload);
    WalletDto walletDto = new Gson().fromJson(payload, WalletDto.class);
    WalletDto response = walletService.createWallet(walletDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
