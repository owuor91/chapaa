package owuor91.io.transactions.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.mapper.UserMapper;
import owuor91.io.transactions.model.User;
import owuor91.io.transactions.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired UserRepository userRepository;
  @Autowired UserMapper userMapper;

  @Override public UserDto createUser(UserDto userDto) {
    return userMapper.toUserDto(userRepository.save(userMapper.toUser(userDto)));
  }

  @Override public UserDto findDefaultSystemUser() throws UserNotFoundException {
    Optional<User> defaultUser = userRepository.findByName("Default System");
    if (defaultUser.isPresent()) {
      return userMapper.toUserDto(defaultUser.get());
    }
    throw new UserNotFoundException("Default user not found");
  }
}
