package owuor91.io.transactions.mapper;

import org.springframework.stereotype.Component;
import owuor91.io.transactions.dto.UserDto;
import owuor91.io.transactions.model.User;

@Component
public class UserMapper {
  public User toUser(UserDto userDto) {
    return User.builder()
        .userId(userDto.getUserId())
        .name(userDto.getName())
        .phoneNumber(userDto.getPhoneNumber())
        .pin(userDto.getPin())
        .build();
  }

  public UserDto toUserDto(User user) {
    return UserDto.builder()
        .userId(user.getUserId())
        .name(user.getName())
        .phoneNumber(user.getPhoneNumber())
        .pin(user.getPin())
        .build();
  }
}
