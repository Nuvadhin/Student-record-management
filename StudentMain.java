import mypack.Student;
import java.util.Scanner;

public class StudentMain {
    public static void main(String[] args) {
        final int TOTAL = 72;   // number of students
        Scanner sc = new Scanner(System.in);

        Student[] students = new Student[TOTAL];

        // Using for loop to read student details
        for (int i = 0; i < TOTAL; i++) {
            System.out.println("\nEnter details of Student " + (i + 1));

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Roll No: ");
            int roll = sc.nextInt();

            System.out.print("OOP Marks: ");
            int marks = sc.nextInt();
            sc.nextLine(); // consume newline

            students[i] = new Student(name, roll, marks);
        }

        // Print all details
        System.out.println("\n--- Student Details ---");
        for (Student s : students) {
            s.show();
        }

        sc.close();
    }
}
