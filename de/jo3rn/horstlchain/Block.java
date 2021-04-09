package de.jo3rn.horstlchain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Block {
  private Exam exam;
  private String hash;
  private String previousHash;
  private String timestamp;
  private int nonce = Integer.MIN_VALUE;

  public Block(Exam exam, String previousHash) {
    this.exam = exam;
    this.previousHash = previousHash;

    this.timestamp = ZonedDateTime
        .now(ZoneId.of("Europe/Paris"))
        .format(DateTimeFormatter.ofPattern("uuuu.MM.dd-HH:mm:ss' (der 'D'. Tag des Jahres)'"));

    this.hash = calculateHash();
  }

  public String calculateHash() {
    return Utils.getSha3256Hash(
        exam.toString()
            + previousHash
            + timestamp
            + nonce);
  }

  public String mineBlock(String difficulty) {
    while (!this.hash.startsWith(difficulty)) {
      nonce++;
      this.hash = calculateHash();
    }
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
    return "Block:"
        + "\nexam=" + exam
        + "\nhash='" + hash + '\''
        + "\npreviousHash='" + previousHash + '\''
        + "\ntimestamp='" + timestamp + '\''
        + "\nnonce='" + nonce + '\'';
  }
}