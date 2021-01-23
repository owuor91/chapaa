package owuor91.io.transactions.mapper;

import org.springframework.stereotype.Component;
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.model.Wallet;

@Component
public class WalletMapper {
  public Wallet toWallet(WalletDto walletDto){
    return Wallet.builder()
        .walletId(walletDto.getWalletId())
        .balance(walletDto.getBalance())
        .user(walletDto.getUser())
        .build();
  }

  public WalletDto toWalletDto(Wallet wallet){
    return WalletDto.builder()
        .walletId(wallet.getWalletId())
        .balance(wallet.getBalance())
        .user(wallet.getUser())
        .userId(wallet.getUser().getUserId())
        .build();
  }
}
