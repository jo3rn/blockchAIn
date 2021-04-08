package de.jo3rn.horstlchain.gui;

import de.jo3rn.horstlchain.ExamAttendance;
import de.jo3rn.horstlchain.HorstlChain;

import javax.swing.SwingWorker;

public class AddBlockTask extends SwingWorker<Void, Void> {

  private final ExamAttendance examAttendance;
  private final HorstlChain chain;

  public AddBlockTask(ExamAttendance examAttendance, HorstlChain horstlChain) {
    this.examAttendance = examAttendance;
    this.chain = horstlChain;
  }

  /*
   * This task adds a block to the chain in a background thread.
   */
  @Override
  protected Void doInBackground() throws Exception {
    chain.addBlock(examAttendance);
    return null;
  }
}
