import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

  // https://www.baeldung.com/sha-256-hashing-java
  public static String getSha3256Hash(String originalString) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA3-256");
      byte[] encodedHash = digest.digest(
          originalString.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(encodedHash);

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException();
    }
  }

  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
