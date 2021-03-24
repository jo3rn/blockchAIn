import java.time.LocalDate;

public class HorstlChain {
  private Block[] horstlChain;
  private int currentIndex = 0;

  public HorstlChain(ExamAttendance examAttendance) {
    horstlChain = new Block[100];
    horstlChain[0] = new Block(examAttendance, "genesis");
  }

  private void addBlock(ExamAttendance examAttendance) {
    String previousHash = horstlChain[currentIndex].getHash();
    horstlChain[++currentIndex] = new Block(examAttendance, previousHash);
  }

  public static void main(String[] args) {
    ExamAttendance genesisExamAttendance = new ExamAttendance(
        123456,
        "Programmierung 1",
        2.7,
        LocalDate.of(2021, 2, 20)
    );

    HorstlChain chain = new HorstlChain(genesisExamAttendance);

    ExamAttendance examAttendance1 = new ExamAttendance(
        133744,
        "Programmierung 1",
        1.3,
        LocalDate.of(2021, 2, 20)
    );
    chain.addBlock(examAttendance1);

    ExamAttendance examAttendance2 = new ExamAttendance(
        424242,
        "Programmierung 1",
        4.0,
        LocalDate.of(2021, 2, 20)
    );
    chain.addBlock(examAttendance2);

    System.out.println(chain);
  }

  @Override
  public String toString() {
    String result = "";
    for (int i = 0; i <= currentIndex; i++) {
      result += "\n\nBlock " + i + "\n" + horstlChain[i];
    }

    return result;
  }
}
