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

  private boolean isValid() {
    Block currentBlock;
    Block previousBlock;

    int i = 1;
    while (i <= currentIndex) {
      currentBlock = horstlChain[i];
      previousBlock = horstlChain[i - 1];

      // stored hash does not equal calculated hash
      if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
        System.out.println("Block " + i + " is not valid.");
        return false;
      }

      // stored previousHash does not equal actual previous hash
      if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
        System.out.println("Block " + i + " has not matching previousHash.");
        return false;
      }

      i++;
    }

    System.out.println("Chain is valid.");
    return true;
  }

  private void corruptChain() {
    int blockToCorrupt = currentIndex - 2;
    horstlChain[blockToCorrupt] = new Block(
        ExamAttendance.getRandomAttendance(),
        horstlChain[blockToCorrupt - 1].getHash());
    System.out.println("Block " + blockToCorrupt + " has been changed.");
  }

  public static void main(String[] args) {
    ExamAttendance genesisExamAttendance = new ExamAttendance(
        123456,
        "Programmierung 1",
        2.7,
        LocalDate.of(2021, 2, 20)
    );

    HorstlChain chain = new HorstlChain(genesisExamAttendance);

    chain.addBlock(ExamAttendance.getRandomAttendance());
    chain.addBlock(ExamAttendance.getRandomAttendance());
    chain.addBlock(ExamAttendance.getRandomAttendance());
    chain.addBlock(ExamAttendance.getRandomAttendance());
    chain.addBlock(ExamAttendance.getRandomAttendance());
    chain.addBlock(ExamAttendance.getRandomAttendance());

    chain.isValid();

    chain.corruptChain();
    System.out.println(chain);
    chain.isValid();
  }

  @Override
  public String toString() {
    String result = "\nCurrent state of the horstl chain:\n";
    for (int i = 0; i <= currentIndex; i++) {
      result += "\nBlock " + i + "\n" + horstlChain[i] + "\n";
    }

    return result;
  }
}
