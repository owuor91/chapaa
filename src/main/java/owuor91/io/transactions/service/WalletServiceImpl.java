package owuor91.io.transactions.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.mapper.WalletMapper;
import owuor91.io.transactions.model.User;
import owuor91.io.transactions.model.Wallet;
import owuor91.io.transactions.repository.UserRepository;
import owuor91.io.transactions.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {
  @Autowired WalletMapper walletMapper;
  @Autowired WalletRepository walletRepository;
  @Autowired UserRepository userRepository;

  @Override public WalletDto createWallet(WalletDto walletDto) throws UserNotFoundException {
    Optional<User> userOptional = userRepository.findById(walletDto.getUserId());
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (user.getWallet() != null) {
        return walletMapper.toWalletDto(user.getWallet());
      }
      Wallet wallet = walletMapper.toWallet(walletDto);
      wallet.setUser(userOptional.get());
      wallet.setBalance(0.0);
      wallet = walletRepository.save(wallet);
      user.setWallet(wallet);
      userRepository.save(user);
      return walletMapper.toWalletDto(wallet);
    }
    throw new UserNotFoundException("User doesn't exist");
  }
}
