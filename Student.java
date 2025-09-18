package mypack;
import java.util.Scanner;

public class Student {
    String name;
    int rollNo;
    int seriesMark;

    // Method to read student details
    public void readDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        name = sc.nextLine();
        System.out.print("Enter roll number: ");
        rollNo = sc.nextInt();
        System.out.print("Enter series mark: ");
        seriesMark = sc.nextInt();
    }

    // Method to display details
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Series Mark: " + seriesMark);
        System.out.println("---------------------------");
    }
}
