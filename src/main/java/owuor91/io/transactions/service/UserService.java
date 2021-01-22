package owuor91.io.transactions.service;

import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.model.User;

public interface UserService {
  UserDto createUser(UserDto userDto);
}
