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

    chain.addRandomBlocks(chain, 6);

    System.out.println("Chain is " + (chain.isValid() ? "" : "not ") + "valid.");

    chain.corruptChain();
    System.out.println(chain);
    System.out.println("Chain is " + (chain.isValid() ? "" : "not ") + "valid.");
  }

  private void addBlock(ExamAttendance examAttendance) {
    if (currentIndex >= horstlChain.length - 1) {
      extendChain();
    }

    String previousHash = horstlChain[currentIndex].getHash();
    Block blockToAdd = new Block(examAttendance, previousHash);
    System.out.println("Block mined with hash: " + blockToAdd.mineBlock(difficulty));
    horstlChain[++currentIndex] = blockToAdd;
  }

  private void extendChain() {
    System.out.println("Chain is full, extending chain...");
    Block[] horstlChainNew = new Block[horstlChain.length + 100];
    for (int i = 0; i < horstlChain.length; i++) {
      horstlChainNew[i] = horstlChain[i];
    }
    horstlChain = horstlChainNew;
  }

  public void addRandomBlocks(HorstlChain chain, int amount) {
    for (int i = 1; i <= amount; i++) {
      chain.addBlock(ExamAttendance.getRandomAttendance());
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
      if(!currentBlock.getHash().startsWith(difficulty)) {
        System.out.println("Difficulty doesn't match on pos " + i);
        System.out.println("The difficulty was: " + difficulty);
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
