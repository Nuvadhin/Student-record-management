import java.util.ArrayList;

public class StudentRecordSystem {
    private ArrayList<Student> students;

    public StudentRecordSystem() {
        students = new ArrayList<>();
    }

    public void addStudent(String id, String name, int age, String grade) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                System.out.println("Student ID already exists.");
                return;
            }
        }
        students.add(new Student(id, name, age, grade));
        System.out.println("Student added successfully.");
    }

    public String getAllStudentsAsString() {
        if (students.isEmpty()) {
            return "No students found.";
        }
        StringBuilder sb = new StringBuilder("Student Records:\n");
        for (Student s : students) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }

    public void updateStudent(String id, String name, Integer age, String grade) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                if (name != null) s.setName(name);
                if (age != null) s.setAge(age);
                if (grade != null) s.setGrade(grade);
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void deleteStudent(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public Student searchStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }
}