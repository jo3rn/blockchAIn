package de.jo3rn.horstlchain;

import de.jo3rn.horstlchain.gui.ChainForm;

import java.time.LocalDate;

public class HorstlChain {
  private Block[] horstlChain;
  private int currentIndex = 0;
  private String difficulty = "00";

  public HorstlChain(Exam genesisExam) {
    horstlChain = new Block[100];
    horstlChain[0] = new Block(genesisExam, "genesis");
  }

  public static void main(String[] args) {
    Exam genesisExam = new Exam("Programmierung 1", LocalDate.of(2021, 2, 20), new ExamAttendance[0]);
    HorstlChain chain = new HorstlChain(genesisExam);

    chain.corruptChain();

    chain.addRandomBlocks(6);

    chain.printExamsForModule("Prog1");

    System.out.println("Chain is " + (chain.isValid() ? "" : "not ") + "valid.");
    System.out.println(chain);

    ChainForm.createGui(chain);
  }

  public void addBlock(Exam exam) {
    if (currentIndex >= horstlChain.length - 1) {
      extendChain();
    }

    String previousHash = horstlChain[currentIndex].getHash();
    Block blockToAdd = new Block(exam, previousHash);
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

  public void printExamsForModule(String moduleTitle) {
    System.out.println("Searching for exams of " + moduleTitle);
    int found = 0;
    for (int i = 0; i < currentIndex; i++) {
      Exam currentExam = horstlChain[i].getExam();
      if (currentExam.getModuleTitle().equals(moduleTitle)) {
        found++;
        System.out.println(currentExam);
      }
    }

    if (found == 0) {
      System.out.println("No exam found for " + moduleTitle);
    } else {
      System.out.println("Found " + found + " exams for " + moduleTitle);
    }
  }

  public void addRandomBlocks(int amount) {
    for (int i = 1; i <= amount; i++) {
      this.addBlock(Exam.getRandomExam());
    }
  }

  public boolean isValid() {
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

  public void corruptChain() {
    if (currentIndex < 1) {
      System.out.println("Chain needs to have at least 2 blocks to be corrupted.");
    } else {
      /* block to corrupt should be between the second and the last block in the chain */
      int randomIndex = (int) (Math.random() * (currentIndex - 1) + 1);
      Block corruptBlock = new Block(Exam.getRandomExam(), horstlChain[randomIndex - 1].getHash());
      horstlChain[randomIndex] = corruptBlock;
      System.out.println("Block " + randomIndex + " has been corrupted.");
    }
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
