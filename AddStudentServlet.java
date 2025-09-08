import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String course = request.getParameter("course");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb", "root", "password");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students(id, name, age, course) VALUES (?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);
            ps.setInt(3, Integer.parseInt(age));
            ps.setString(4, course);

            int i = ps.executeUpdate();
            if (i > 0) {
                    out.println("<h3>✅ Student Added Successfully!</h3>");
            } else {
                out.println("<h3>❌ Failed to Add Student.</h3>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }

        out.close();
    }
}