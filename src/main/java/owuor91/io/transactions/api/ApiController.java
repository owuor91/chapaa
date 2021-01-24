package owuor91.io.transactions.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import owuor91.io.transactions.exceptions.WrongPinException;
import owuor91.io.transactions.service.UserService;

@Controller
@RequestMapping("/api")
public abstract class ApiController {
  @Autowired UserService userService;

  public void validateUser(String phoneNumber, String pin) throws WrongPinException {
    if (!userService.userIsValid(phoneNumber, pin)) {
      throw new WrongPinException("Invalid credentials, please try again");
    }
  }
}
