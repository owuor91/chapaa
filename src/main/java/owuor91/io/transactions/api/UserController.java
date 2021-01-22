package owuor91.io.transactions.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.model.User;
import owuor91.io.transactions.service.UserService;

@RestController
public class UserController extends ApiController{
  @Autowired UserService userService;

  @PostMapping(value = "/user", produces = "application/json")
  public ResponseEntity<Object> createUser(@RequestBody String payload){
    System.out.println(payload);
    UserDto userDto = new Gson().fromJson(payload, UserDto.class);
    UserDto response = userService.createUser(userDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
