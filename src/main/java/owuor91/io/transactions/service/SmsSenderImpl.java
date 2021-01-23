package owuor91.io.transactions.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import owuor91.io.transactions.dto.BalanceDto;
import owuor91.io.transactions.dto.TransactionDto;
import owuor91.io.transactions.dto.WalletDto;
import owuor91.io.transactions.exceptions.UserNotFoundException;
import owuor91.io.transactions.util.Constants;
import owuor91.io.transactions.util.Util;

@Component
public class SmsSenderImpl implements SmsSender {
  @Autowired WalletService walletService;
  private Map<String, String> templatesMap = templatesMap();

  @Override public void sendSms(String content, String recipient) {
    Dotenv dotenv = Dotenv.load();
    Twilio.init(dotenv.get(Constants.TWILIO_ACCOUNT_SID), dotenv.get(Constants.TWILIO_AUTH_TOKEN));
    PhoneNumber from = new PhoneNumber(dotenv.get(Constants.TWILIO_PHONE_NUMBER));
    PhoneNumber to = new PhoneNumber(recipient);
    Message message = Message.creator(to, from, content).create();
    System.out.println(message.getSid());
  }

  @Override public String composeDepositMessage(TransactionDto trx) {
    return composeFundWithdrawMessage(trx, trx.getReceiver(), templatesMap.get(Constants.DEPOSIT));
  }

  @Override public String composeWithdrawMessage(TransactionDto trx) {
    return composeFundWithdrawMessage(trx, trx.getSender(), templatesMap.get(Constants.WITHDRAW));
  }

  @Override public String composeSenderMessage(TransactionDto trx) {
    return composeTransferMessage(trx, true, templatesMap.get(Constants.SEND));
  }

  @Override public String composeReceiptMessage(TransactionDto trx) {
    return composeTransferMessage(trx, false, templatesMap.get(Constants.RECEIVE));
  }

  @Override public String composeFundWithdrawMessage(TransactionDto trx, String phoneNumber,
      String template) {
    Double balance = getWallet(phoneNumber).getBalance();
    String[] time = getTime(trx);
    return String.format(template, trx.getTransactionCode(), trx.getAmount(), time[0], time[1],
        balance);
  }

  @Override
  public String composeTransferMessage(TransactionDto trx, boolean send, String template) {
    WalletDto senderWallet = getWallet(trx.getSender());
    WalletDto receiverWallet = getWallet(trx.getReceiver());
    String recipient = String.format("%s (%s)", receiverWallet.getUser().getName(),
        receiverWallet.getUser().getPhoneNumber());
    String sender = String.format("%s (%s)", senderWallet.getUser().getName(),
        senderWallet.getUser().getPhoneNumber());
    String name = send ? recipient : sender;
    String[] time = getTime(trx);
    Double balance = send ? senderWallet.getBalance() : receiverWallet.getBalance();
    return String.format(
        template, trx.getTransactionCode(), trx.getAmount(), name, time[0], time[1],
        balance);
  }

  @Override public WalletDto getWallet(String phoneNumber) {
    try {
      return walletService.getWalletByPhoneNumber(phoneNumber);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public String[] getTime(TransactionDto transactionDto) {
    return Util.getDateTimeFromTimeStamp(transactionDto.getTimestamp());
  }

  private Map<String, String> templatesMap() {
    Map<String, String> templatesMap = new HashMap<>();
    templatesMap.put(Constants.DEPOSIT,
        "%s Confirmed. Deposited KES %.2f to your wallet on %s at %s. Your new balance is KES %.2f.");
    templatesMap.put(Constants.WITHDRAW,
        "%s Confirmed. Withdraw KES %.2f from your wallet on %s at %s. Your new balance is KES %.2f.");
    templatesMap.put(Constants.SEND,
        "%s Confirmed. Sent KES %.2f to %s on %s at %s. Your new balance is KES %.2f.");
    templatesMap.put(Constants.RECEIVE,
        "%s Confirmed. Received KES %.2f from %s on %s at %s. Your new balance is KES %.2f.");
    templatesMap.put(Constants.BALANCE, "Confirmed. Your wallet balance was KES %.2f on %s at %s.");
    return templatesMap;
  }

  @Override public String composeBalanceMessage(BalanceDto balanceDto) {
    String[] time = Util.getDateTimeFromTimeStamp(balanceDto.timestamp);
    return String.format(templatesMap.get(Constants.BALANCE), balanceDto.balance, time[0], time[1]);
  }
}
