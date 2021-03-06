package owuor91.io.transactions.util;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
  public static String generateRandomCode(boolean pin) {
    String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int size = 8;
    if (pin) {
      source = "0123456789";
      size = 4;
    }
    SecureRandom secureRandom = new SecureRandom();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      stringBuilder.append(source.charAt(secureRandom.nextInt(source.length())));
    }
    return stringBuilder.toString();
  }

  public static String formatPhoneNumber(String phoneNumber) {
    if (phoneNumber.startsWith("0")) {
      return "+254".concat(phoneNumber.substring(1));
    } else if (phoneNumber.startsWith("254")) {
      return "+".concat(phoneNumber);
    } else if (phoneNumber.startsWith("+254")) {
      return phoneNumber;
    } else {
      return phoneNumber;
    }
  }

  public static String[] getDateTimeFromTimeStamp(Timestamp timestamp) {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy");
    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm aa");
    Date mdate = new Date(timestamp.getTime());
    String date = dateFormatter.format(mdate);
    String time = timeFormatter.format(mdate);
    return new String[] { date, time };
  }
}
