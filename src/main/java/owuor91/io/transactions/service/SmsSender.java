package owuor91.io.transactions.service;

import owuor91.io.transactions.dto.BalanceDto;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.dto.WalletDto;

public interface SmsSender {
  void sendSms(String content, String recipient);

  String composeDepositMessage(TransactionDto trx);

  String composeWithdrawMessage(TransactionDto trx);

  String composeSenderMessage(TransactionDto trx);

  String composeReceiptMessage(TransactionDto trx);

  String composeFundWithdrawMessage(TransactionDto trx, String phoneNumber, String template);

  String composeTransferMessage(TransactionDto trx, boolean send, String template);

  WalletDto getWallet(String phoneNumber);

  String[] getTime(TransactionDto transactionDto);

  String composeBalanceMessage(BalanceDto balanceDto);

  String composeNewPinMessage(String newPin);
}
