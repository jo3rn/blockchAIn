package de.jo3rn.horstlchain;

public class ExamAttendance {
  private int matriculationNumber;
  private double grade;

  public ExamAttendance(
      int matriculationNumber,
      double grade) {
    this.matriculationNumber = matriculationNumber;
    this.grade = grade;
  }

  public static ExamAttendance getRandomAttendance() {
    int lowerBound = 100000;
    int upperBound = 9999999;
    int randomMatriculation = lowerBound + (int) (Math.random() * ((upperBound - lowerBound) + 1));

    double[] grades = new double[]{ 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 5.0 };
    double randomGrade = grades[(int) (Math.random() * grades.length)];

    return new ExamAttendance(randomMatriculation, randomGrade);
  }

  @Override
  public String toString() {
    return "ExamAttendance: "
        + "matriculationNumber=" + matriculationNumber
        + ", grade=" + grade;
  }
}
