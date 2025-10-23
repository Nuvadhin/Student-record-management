import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root"; // change if needed
    private static final String PASSWORD = "yourpassword"; // replace with your MySQL password

    // Get database connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load JDBC driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database Connected Successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed.");
            e.printStackTrace();
        }
        return conn;
    }

    // Example method to insert student
    public static void insertStudent(String name, int age, String gender, String course, String dept, String email, String phone, String address) {
        String sql = "INSERT INTO students (name, age, gender, course, department, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, course);
            stmt.setString(5, dept);
            stmt.setString(6, email);
            stmt.setString(7, phone);
            stmt.setString(8, address);
            stmt.executeUpdate();
            System.out.println("✅ Student Added Successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error inserting student.");
            e.printStackTrace();
        }
    }

    // Example method to display all students
    public static void showAllStudents() {
        String sql = "SELECT * FROM students";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n=== Student List ===");
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("course") + " | " +
                                   rs.getString("department"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching students.");
            e.printStackTrace();
        }
    }

    // Test connection
    public static void main(String[] args) {
        getConnection();
        showAllStudents();
    }
}