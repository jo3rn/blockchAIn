import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Block {
  private ExamAttendance examAttendance;
  private String hash;
  private String previousHash;
  private String timestamp;
  private int nonce = Integer.MIN_VALUE;

  public Block(ExamAttendance examAttendance, String previousHash) {
    this.examAttendance = examAttendance;
    this.previousHash = previousHash;

    this.timestamp = ZonedDateTime
        .now(ZoneId.of("Europe/Paris"))
        .format(DateTimeFormatter.ofPattern("uuuu.MM.dd-HH:mm:ss"));

    this.hash = calculateHash();
  }

  public String calculateHash() {
    return Utils.getSha3256Hash(
        examAttendance.toString()
            + previousHash
            + timestamp
            + nonce);
  }

  public String mineBlock(String difficulty) {
    while (!this.hash.startsWith(difficulty)) {
      nonce++;
      this.hash = calculateHash();
    }
    System.out.println("used nonce: " + this.nonce);
    return this.hash;
  }

  public String getHash() {
    return this.hash;
  }

  public String getPreviousHash() {
    return this.previousHash;
  }

  @Override
  public String toString() {
    return "Block{" +
        "examAttendance=" + examAttendance +
        "\nhash='" + hash + '\'' +
        "\npreviousHash='" + previousHash + '\'' +
        "\ntimestamp='" + timestamp + '\'' +
        '}';
  }
}