package de.jo3rn.horstlchain.gui;

import de.jo3rn.horstlchain.Exam;
import de.jo3rn.horstlchain.HorstlChain;

import javax.swing.SwingWorker;

public class AddBlockTask extends SwingWorker<Void, Void> {

  private final Exam exam;
  private final HorstlChain chain;

  public AddBlockTask(Exam exam, HorstlChain horstlChain) {
    this.exam = exam;
    this.chain = horstlChain;
  }

  /*
   * This task adds a block to the chain in a background thread.
   */
  @Override
  protected Void doInBackground() throws Exception {
    chain.addBlock(exam);
    return null;
  }
}
