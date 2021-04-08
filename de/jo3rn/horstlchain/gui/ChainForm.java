package de.jo3rn.horstlchain.gui;

import de.jo3rn.horstlchain.ExamAttendance;
import de.jo3rn.horstlchain.HorstlChain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ChainForm extends JFrame {
  private final JPanel mainPanel;

  private JTextField txtMatriculationNumber;
  private JTextField txtModuleTitle;
  private JTextField txtGrade;
  private JTextField txtExamDateDay;
  private JTextField txtExamDateMonth;
  private JTextField txtExamDateYear;
  private JButton btnAddBlock;
  private JProgressBar prgAddBlock;
  private JTextPane txpChain;

  private final HorstlChain chain;

  private final ActionListener btnAddBlockActionListener = e -> runAddBlockTask();

  private final ActionListener btnShowChainActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      txpChain.setText(chain.toString());
    }
  };

  public ChainForm(String title, HorstlChain chain) {
    super(title);

    this.chain = chain;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height * 2 / 3;
    int width = screenSize.width / 3;
    this.setSize(new Dimension(width, height));

    mainPanel = new JPanel();
    this.setContentPane(mainPanel);
    initComponents(mainPanel);

    setMiningUi(false);
  }


  private ExamAttendance getExamAttendanceFromForm() {
    int matriculationNumber = Integer.parseInt(txtMatriculationNumber.getText());
    String moduleTitle = txtModuleTitle.getText();
    double grade = Double.parseDouble(txtGrade.getText());
    int day = Integer.parseInt(txtExamDateDay.getText());
    int month = Integer.parseInt(txtExamDateMonth.getText());
    int year = Integer.parseInt(txtExamDateYear.getText());
    LocalDate examDate = LocalDate.of(year, month, day);
    return new ExamAttendance(matriculationNumber, moduleTitle, grade, examDate);
  }

  private void runAddBlockTask() {
    setMiningUi(true);
    AddBlockTask addBlockTask = new AddBlockTask(getExamAttendanceFromForm(), chain);
    addBlockTask.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals("state") && evt.getNewValue().equals(SwingWorker.StateValue.DONE)) {
        setMiningUi(false);
        txpChain.setText(chain.toString());
      }
    });
    addBlockTask.execute();
  }

  private void setMiningUi(boolean isMining) {
    if (isMining) {
      btnAddBlock.setEnabled(false);
      prgAddBlock.setIndeterminate(true);
      prgAddBlock.setString("Mining block...");
      this.mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    } else {
      btnAddBlock.setEnabled(true);
      prgAddBlock.setIndeterminate(false);
      prgAddBlock.setString("No block is being mined.");
      this.mainPanel.setCursor(Cursor.getDefaultCursor());
    }
  }

  public void initComponents(Container pane) {

    pane.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    final double WEIGHT_LABEL = 0.2;
    final double WEIGHT_TEXT_FIELD = 0.5;

    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 10, 10, 10);

    // FIRST ROW
    c.gridy = 0;
    c.gridx = 0;

    JLabel lblMatriculationNumber = new JLabel("Matrikelnummer", SwingConstants.RIGHT);
    c.weightx = WEIGHT_LABEL;
    c.anchor = GridBagConstraints.FIRST_LINE_START;
    pane.add(lblMatriculationNumber, c);

    txtMatriculationNumber = new JTextField();
    c.weightx = WEIGHT_TEXT_FIELD;
    c.gridx = 1;
    pane.add(txtMatriculationNumber, c);

    JLabel lblModuleTitle = new JLabel("Modultitel", SwingConstants.RIGHT);
    c.weightx = WEIGHT_LABEL;
    c.gridx = 2;
    pane.add(lblModuleTitle, c);

    txtModuleTitle = new JTextField();
    c.weightx = WEIGHT_TEXT_FIELD;
    c.gridx = 3;
    pane.add(txtModuleTitle, c);

    JLabel lblGrade = new JLabel("Note", SwingConstants.RIGHT);
    c.weightx = WEIGHT_LABEL;
    c.gridx = 4;
    pane.add(lblGrade, c);

    txtGrade = new JTextField();
    c.weightx = WEIGHT_TEXT_FIELD;
    c.gridx = 5;
    pane.add(txtGrade, c);

    // SECOND ROW
    c.gridy = 1;
    c.gridx = 0;

    JLabel lblExamDateDay = new JLabel("Tag", SwingConstants.RIGHT);
    c.weightx = WEIGHT_LABEL;
    pane.add(lblExamDateDay, c);

    txtExamDateDay = new JTextField();
    c.weightx = WEIGHT_TEXT_FIELD;
    c.gridx = 1;
    pane.add(txtExamDateDay, c);

    JLabel lblExamDateMonth = new JLabel("Monat", SwingConstants.RIGHT);
    c.weightx = WEIGHT_LABEL;
    c.gridx = 2;
    pane.add(lblExamDateMonth, c);

    txtExamDateMonth = new JTextField();
    c.weightx = WEIGHT_TEXT_FIELD;
    c.gridx = 3;
    pane.add(txtExamDateMonth, c);

    JLabel lblExamDateYear = new JLabel("Jahr", SwingConstants.RIGHT);
    c.weightx = WEIGHT_LABEL;
    c.gridx = 4;
    pane.add(lblExamDateYear, c);

    txtExamDateYear = new JTextField();
    c.weightx = WEIGHT_TEXT_FIELD;
    c.gridx = 5;
    pane.add(txtExamDateYear, c);

    // THIRD ROW
    c.gridy = 2;
    c.gridx = 0;

    btnAddBlock = new JButton("Block hinzufügen");
    c.weightx = 0.0;
    c.gridwidth = 6;
    pane.add(btnAddBlock, c);
    btnAddBlock.addActionListener(btnAddBlockActionListener);

    // FOURTH ROW
    c.gridy = 3;
    c.gridx = 0;

    prgAddBlock = new JProgressBar();
    c.weightx = 0.0;
    c.insets = new Insets(10, 10, 20, 10);
    pane.add(prgAddBlock, c);
    prgAddBlock.setStringPainted(true);

    // FIFTH ROW
    c.gridy = 4;
    c.gridx = 0;

    JButton btnShowChain = new JButton("Horstlchain anzeigen");
    c.weightx = 1.0;
    c.insets = new Insets(0, 10, 10, 10);
    pane.add(btnShowChain, c);
    btnShowChain.addActionListener(btnShowChainActionListener);

    // SIXTH ROW
    c.gridy = 5;
    c.gridx = 0;
    c.weighty = 1;
    c.weightx = 1;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(0, 10, 10, 10);
    JScrollPane scrChain = new JScrollPane();
    pane.add(scrChain, c);
    txpChain = new JTextPane();
    txpChain.setFont(new Font("SansSerif", Font.PLAIN, 20));
    scrChain.setViewportView(txpChain);
  }

  public static void createGui(HorstlChain chain) {
    ChainForm frame = new ChainForm("Horstl Chain", chain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
