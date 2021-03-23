import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Block {
  private ExamAttendance examAttendance;
  private String hash; // fingerprint
  private String previousHash;
  private String timestamp; // "2021.02.20-11:00:00"

  public Block(ExamAttendance examAttendance, String previousHash) {
    this.examAttendance = examAttendance;
    this.previousHash = previousHash;

    this.hash = calculateHash();

    this.timestamp = ZonedDateTime
        .now(ZoneId.of("Europe/Paris"))
        .format(DateTimeFormatter.ofPattern("uuuu.MM.dd-HH:mm:ss"));
  }

  private String calculateHash() {
    return "343950348920456843ß523157213589234573894534345t";
  }

  public static void main(String[] args) {
    ExamAttendance examAttendance = new ExamAttendance(
        123456,
        "Programmierung 1",
        2.7,
        LocalDate.of(2021, 2, 20)
    );

    Block block = new Block(examAttendance, "previousHash");
    System.out.println(block);
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
