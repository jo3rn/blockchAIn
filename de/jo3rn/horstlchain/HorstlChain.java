package de.jo3rn.horstlchain;

import java.time.LocalDate;

public class HorstlChain {
  private Block[] horstlChain;
  private int currentIndex = 0;
  private String difficulty = "00000";

  public HorstlChain(ExamAttendance genesisExamAttendance) {
    horstlChain = new Block[100];
    horstlChain[0] = new Block(genesisExamAttendance, "genesis");
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

    System.out.println("Chain is " + (chain.isValid() ? "" : "not ") + "valid.");

    chain.corruptChain();
    System.out.println(chain);
    System.out.println("Chain is " + (chain.isValid() ? "" : "not ") + "valid.");
  }

  private void addBlock(ExamAttendance examAttendance) {
    if (currentIndex < horstlChain.length - 1) {
      String previousHash = horstlChain[currentIndex].getHash();
      Block blockToAdd = new Block(examAttendance, previousHash);
      System.out.println("Block mined with hash: " + blockToAdd.mineBlock(difficulty));
      horstlChain[++currentIndex] = blockToAdd;
    } else {
      System.out.println("Chain is full.");
    }
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

    return true;
  }

  private void corruptChain() {
    int blockToCorrupt = currentIndex - 2;
    horstlChain[blockToCorrupt] = new Block(
        ExamAttendance.getRandomAttendance(),
        horstlChain[blockToCorrupt - 1].getHash());
    System.out.println("Block " + blockToCorrupt + " has been changed.");
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
