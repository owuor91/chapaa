package owuor91.io.transactions.util;

import java.security.SecureRandom;

public class Util {
  public static String generateRandomCode() {
    String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    SecureRandom secureRandom = new SecureRandom();
    StringBuilder stringBuilder = new StringBuilder(6);
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(source.charAt(secureRandom.nextInt(source.length())));
    }
    return stringBuilder.toString();
  }
}
