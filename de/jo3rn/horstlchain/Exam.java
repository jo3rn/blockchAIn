package de.jo3rn.horstlchain;

import java.time.LocalDate;
import java.util.Arrays;

public class Exam {
  private String moduleTitle;
  private LocalDate examDate;

  private ExamAttendance[] examAttendances;

  public Exam(String moduleTitle, LocalDate examDate, ExamAttendance[] examAttendances) {
    this.moduleTitle = moduleTitle;
    this.examDate = examDate;
    this.examAttendances = examAttendances;
  }

  public static Exam getRandomExam() {
    String[] moduleTitles = { "Prog1", "Prog2", "Mathe"};
    String randomModuleTitel = moduleTitles[(int) (Math.random() * moduleTitles.length)];

    ExamAttendance[] examAttendances = new ExamAttendance[5];
    for (int i = 0; i < 5; i++) {
      examAttendances[i] = ExamAttendance.getRandomAttendance();
    }

    return new Exam(randomModuleTitel, LocalDate.now(), examAttendances);
  }

  public String getModuleTitle() {
    return moduleTitle;
  }

  @Override
  public String toString() {
    return "Exam{" +
        "moduleTitle='" + moduleTitle + '\'' +
        ", examDate=" + examDate +
        ", examAttendances=" + Arrays.toString(examAttendances) +
        '}';
  }
}
