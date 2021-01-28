package owuor91.io.transactions.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owuor91.io.transactions.dto.PinResetResponse;
import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.mapper.UserMapper;
import owuor91.io.transactions.model.User;
import owuor91.io.transactions.repository.UserRepository;
import owuor91.io.transactions.util.Util;

@Service
public class UserServiceImpl implements UserService {
  @Autowired UserRepository userRepository;
  @Autowired UserMapper userMapper;

  @Override public UserDto createUser(UserDto userDto) {
    User user = userMapper.toUser(userDto);
    user.setPin(hashPin(userDto.getPin()));
    return userMapper.toUserDto(userRepository.save(user));
  }

  @Override public UserDto findDefaultSystemUser() throws UserNotFoundException {
    Optional<User> defaultUser = userRepository.findByName("Default System");
    if (defaultUser.isPresent()) {
      return userMapper.toUserDto(defaultUser.get());
    }
    throw new UserNotFoundException("Default user not found");
  }

  private String hashPin(String pin) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(pin.getBytes());
      return new String(messageDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return pin;
    }
  }

  @Override public boolean userIsValid(String phoneNumber, String pin) {
    Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      String hashed_pin = hashPin(pin);
      boolean valid = hashed_pin.equals(user.getPin());
      return valid;
    }
    return false;
  }

  @Override public PinResetResponse resetPin(String phoneNumber, String email) {
    Optional<User> userOptional = userRepository.findByPhoneNumberAndEmail(phoneNumber, email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      String newpin = Util.generateRandomCode(true);
      user.setPin(hashPin(newpin));
      userRepository.save(user);
      return PinResetResponse.builder()
          .message("Pin reset successful. Check your sms inbox for new pin")
          .newPin(newpin)
          .phoneNumber(user.getPhoneNumber())
          .success(true)
          .build();
    }
    return PinResetResponse.builder()
        .success(false)
        .message("No user found with that email and phone number").build();
  }
}
