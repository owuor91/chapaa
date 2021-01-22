package owuor91.io.transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owuor91.io.transactions.dto.UserDto;
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

}
