public class MainGUI {
  import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame {
    private StudentRecordSystem system;
    private JTextField idField, nameField, ageField, gradeField, searchField;
    private JTextArea displayArea;
    private JButton addButton, viewButton, updateButton, deleteButton, searchButton;

    public MainGUI() {
        system = new StudentRecordSystem();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Record Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        inputPanel.add(new JLabel("Search ID:"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Student");
        viewButton = new JButton("View All");
        updateButton = new JButton("Update Student");
        deleteButton = new JButton("Delete Student");
        searchButton = new JButton("Search Student");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.CENTER);

        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Records"));
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String grade = gradeField.getText().trim();
                if (id.isEmpty() || name.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled.");
                    return;
                }
                system.addStudent(id, name, age, grade);
                clearFields();
                viewStudents();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Age must be a number.");
            }
        });

        viewButton.addActionListener(e -> viewStudents());

        updateButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                Integer age = ageField.getText().trim().isEmpty() ? null : Integer.parseInt(ageField.getText().trim());
                String grade = gradeField.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ID is required for update.");
                    return;
                }
                system.updateStudent(id, name.isEmpty() ? null : name, age, grade.isEmpty() ? null : grade);
                clearFields();
                viewStudents();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Age must be a number.");
            }
        });

        deleteButton.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID is required for deletion.");
                return;
            }
            system.deleteStudent(id);
            clearFields();
            viewStudents();
        });

        searchButton.addActionListener(e -> {
            String id = searchField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Search ID is required.");
                return;
            }
            Student found = system.searchStudentById(id);
            if (found != null)
                displayArea.setText("Search Result:\n" + found.toString());
            else
                displayArea.setText("Student not found.");
        });
    }

    private void viewStudents() {
        displayArea.setText(system.getAllStudentsAsString());
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}  
}
