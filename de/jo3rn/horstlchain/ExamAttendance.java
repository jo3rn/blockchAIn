import java.time.LocalDate;
import java.util.Random;

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

  public static ExamAttendance getRandomAttendance() {
    Random random = new Random();

    int lowerBound = 100000;
    int upperBound = 9999999;
    int randomMatriculation = lowerBound + (int) (Math.random() * ((upperBound - lowerBound) + 1));

    String[] modules = new String[]{ "Programmierung 1", "Programmierung 2", "Mathe", "BWL" };
    String randomModule = modules[random.nextInt(modules.length)];

    double[] grades = new double[]{ 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 5.0 };
    double randomGrade = grades[random.nextInt(grades.length)];

    return new ExamAttendance(randomMatriculation, randomModule, randomGrade, LocalDate.now());
  }
}
