package owuor91.io.transactions.service;

import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;

public interface WalletService {
  WalletDto createWallet(WalletDto walletDto) throws UserNotFoundException;
}
