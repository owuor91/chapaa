package owuor91.io.transactions.service;

import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;

public interface UserService {
  UserDto createUser(UserDto userDto);

  UserDto findDefaultSystemUser() throws UserNotFoundException;
}
