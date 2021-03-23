package de.jo3rn.horstlchain;

import java.time.LocalDate;

public class ExamAttendance {
  private int matriculationNumber;
  private String moduleTitle;
  private double grade;
  private LocalDate examDate;

  public ExamAttendance(
      int matriculationNumber,
      String moduleTitle,
      double grade,
      LocalDate examDate) {
    this.matriculationNumber = matriculationNumber;
    this.moduleTitle = moduleTitle;
    this.grade = grade;
    this.examDate = examDate;
  }

  @Override
  public String toString() {
    return "ExamAttendance{" +
        "matriculationNumber=" + matriculationNumber +
        ", moduleTitle='" + moduleTitle + '\'' +
        ", grade=" + grade +
        ", examDate=" + examDate +
        '}';
  }

  public static void main(String[] args) {
    ExamAttendance examAttendance = new ExamAttendance(
        123456,
        "Programmierung 1",
        2.7,
        LocalDate.of(2021, 2, 20)
    );

    System.out.println(examAttendance);
  }
}
