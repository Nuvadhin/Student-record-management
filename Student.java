package mypack;

public class Student {
    String name;
    int rollNo;
    int oopMarks;

    // Constructor
    public Student(String name, int rollNo, int oopMarks) {
        this.name = name;
        this.rollNo = rollNo;
        this.oopMarks = oopMarks;
    }

    // Method to display student details
    public void show() {
        System.out.println("Name: " + name +
                           " | Roll No: " + rollNo +
                           " | OOP Marks: " + oopMarks);
    }
}
