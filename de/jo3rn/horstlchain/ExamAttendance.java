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

  public static ExamAttendance getRandomAttendance() {
    int lowerBound = 100000;
    int upperBound = 9999999;
    int randomMatriculation = lowerBound + (int) (Math.random() * ((upperBound - lowerBound) + 1));

    String[] modules = new String[]{ "Programmierung 1", "Programmierung 2", "Mathe", "BWL" };
    String randomModule = modules[(int) (Math.random() * modules.length)];

    double[] grades = new double[]{ 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 5.0 };
    double randomGrade = grades[(int) (Math.random() * grades.length)];

    return new ExamAttendance(randomMatriculation, randomModule, randomGrade, LocalDate.now());
  }

  @Override
  public String toString() {
    return "ExamAttendance: "
        + "matriculationNumber=" + matriculationNumber
        + ", moduleTitle='" + moduleTitle + '\''
        + ", grade=" + grade
        + ", examDate=" + examDate;
  }
}
