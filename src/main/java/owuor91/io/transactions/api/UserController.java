package owuor91.io.transactions.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.PinResetResponse;
import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.service.SmsSender;
import owuor91.io.transactions.service.UserService;
import owuor91.io.transactions.util.Util;

@RestController
public class UserController extends ApiController{
  @Autowired UserService userService;
  @Autowired SmsSender smsSender;

  @PostMapping(value = "/user", produces = "application/json")
  public ResponseEntity<UserDto> createUser(@RequestBody String payload) {
    System.out.println(payload);
    UserDto userDto = new Gson().fromJson(payload, UserDto.class);
    UserDto response = userService.createUser(userDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping(value = "/user/reset-pin", produces = "application/json")
  public ResponseEntity<PinResetResponse> resetPin(@RequestBody String payload) throws IOException {
    System.out.println(payload);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode tree = mapper.readTree(payload);
    String phoneNumber = tree.get("phoneNumber").textValue();
    String email = tree.get("email").textValue();
    PinResetResponse response = userService.resetPin(phoneNumber, email);
    CompletableFuture.runAsync(() -> {
      smsSender.sendSms(smsSender.composeNewPinMessage(response.getNewPin()),
          Util.formatPhoneNumber(response.getPhoneNumber()));
    });
    return new ResponseEntity<PinResetResponse>(response, HttpStatus.OK);
  }
}
