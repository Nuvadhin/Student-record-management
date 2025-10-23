// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class StudentManagementSystem {
   private static final String URL = "jdbc:mysql://localhost:3306/student_management";
   private static final String USER = "root";
   private static final String PASSWORD = "Add Your Password";
   private static ArrayList<Student> students = new ArrayList();
   private static Scanner scanner;

   public StudentManagementSystem() {
   }

   public static void main(String[] args) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException var2) {
         System.out.println("MySQL JDBC Driver not found.");
         return;
      }

      while(true) {
         displayMenu();
         int choice = getUserChoice();
         switch (choice) {
            case 1:
               addStudent();
               break;
            case 2:
               listStudents();
               break;
            case 3:
               searchStudent();
               break;
            case 4:
               updateStudent();
               break;
            case 5:
               deleteStudent();
               break;
            case 6:
               System.out.println("\nExiting...");
               return;
            default:
               System.out.println("\nInvalid choice. Please enter a number between 1 and 6.");
         }
      }
   }

   private static void displayMenu() {
      System.out.println("\n<< Welcome to Student Management System >>\n");
      System.out.println("1. Add Student");
      System.out.println("2. List Students");
      System.out.println("3. Search Student");
      System.out.println("4. Update Student");
      System.out.println("5. Delete Student");
      System.out.println("6. Exit\n");
      System.out.print("Enter Your Choice: ");
   }

   private static int getUserChoice() {
      int choice = true;

      while(true) {
         try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
         } catch (InputMismatchException var2) {
            System.out.println("\nInvalid choice. Please enter a number between 1 and 6.");
            scanner.nextLine();
            displayMenu();
         }
      }
   }

   private static void addStudent() {
      System.out.println("\n<< Add Student >>");
      int studentId = 0;
      boolean validId = false;

      while(!validId) {
         System.out.print("\nEnter Student ID: ");

         try {
            studentId = Integer.parseInt(scanner.nextLine().trim());
            validId = true;
         } catch (NumberFormatException var13) {
            System.out.println("\nPlease enter a valid numeric student ID.");
         }
      }

      if (isStudentIdTaken(studentId)) {
         System.out.println("\nThis student ID is already taken. Please try again.");
      } else {
         while(true) {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            if (name.matches("[a-zA-Z ]+")) {
               LocalDate birthday;
               while(true) {
                  System.out.print("Enter Birthday (YYYY-MM-DD): ");
                  String birthdayStr = scanner.nextLine();

                  try {
                     birthday = LocalDate.parse(birthdayStr);
                     break;
                  } catch (Exception var17) {
                     System.out.println("\nInvalid date format. Please enter the birthday in YYYY-MM-DD format.\n");
                  }
               }

               while(true) {
                  System.out.print("Enter Email: ");
                  String email = scanner.nextLine();
                  if (email.contains("@") && email.contains(".")) {
                     Student student = new Student(studentId, name, birthday, email);

                     try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "Add Your Password");

                        try {
                           PreparedStatement stmt = conn.prepareStatement("INSERT INTO students (student_id, name, birthday, email) VALUES (?, ?, ?, ?)");

                           try {
                              stmt.setInt(1, student.getStudentId());
                              stmt.setString(2, student.getName());
                              stmt.setDate(3, Date.valueOf(student.getBirthday()));
                              stmt.setString(4, student.getEmail());
                              stmt.executeUpdate();
                              System.out.println("\nStudent added successfully!");
                              students.add(student);
                           } catch (Throwable var14) {
                              if (stmt != null) {
                                 try {
                                    stmt.close();
                                 } catch (Throwable var12) {
                                    var14.addSuppressed(var12);
                                 }
                              }

                              throw var14;
                           }

                           if (stmt != null) {
                              stmt.close();
                           }
                        } catch (Throwable var15) {
                           if (conn != null) {
                              try {
                                 conn.close();
                              } catch (Throwable var11) {
                                 var15.addSuppressed(var11);
                              }
                           }

                           throw var15;
                        }

                        if (conn != null) {
                           conn.close();
                        }
                     } catch (SQLException var16) {
                        System.out.println("\nError adding student: " + var16.getMessage());
                     }

                     return;
                  }

                  System.out.println("\nInvalid email. Please enter a valid email address.\n");
               }
            }

            System.out.println("\nInvalid name. Name can only contain letters. Please try again.\n");
         }
      }
   }

   private static boolean isStudentIdTaken(int studentId) {
      try {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "Add Your Password");

         boolean var4;
         label112: {
            try {
               PreparedStatement stmt;
               label104: {
                  stmt = conn.prepareStatement("SELECT COUNT(*) FROM students WHERE student_id = ?");

                  try {
                     stmt.setInt(1, studentId);
                     ResultSet rs = stmt.executeQuery();

                     label89: {
                        try {
                           if (rs.next() && rs.getInt(1) > 0) {
                              var4 = true;
                              break label89;
                           }
                        } catch (Throwable var9) {
                           if (rs != null) {
                              try {
                                 rs.close();
                              } catch (Throwable var8) {
                                 var9.addSuppressed(var8);
                              }
                           }

                           throw var9;
                        }

                        if (rs != null) {
                           rs.close();
                        }
                        break label104;
                     }

                     if (rs != null) {
                        rs.close();
                     }
                  } catch (Throwable var10) {
                     if (stmt != null) {
                        try {
                           stmt.close();
                        } catch (Throwable var7) {
                           var10.addSuppressed(var7);
                        }
                     }

                     throw var10;
                  }

                  if (stmt != null) {
                     stmt.close();
                  }
                  break label112;
               }

               if (stmt != null) {
                  stmt.close();
               }
            } catch (Throwable var11) {
               if (conn != null) {
                  try {
                     conn.close();
                  } catch (Throwable var6) {
                     var11.addSuppressed(var6);
                  }
               }

               throw var11;
            }

            if (conn != null) {
               conn.close();
            }

            return false;
         }

         if (conn != null) {
            conn.close();
         }

         return var4;
      } catch (SQLException var12) {
         System.out.println("\nError checking student ID: " + var12.getMessage());
         return false;
      }
   }

   private static void listStudents() {
      System.out.println("\n<< Student List >>");

      try {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "Add Your Password");

         try {
            Statement stmt = conn.createStatement();

            try {
               ResultSet rs = stmt.executeQuery("SELECT * FROM students");

               try {
                  boolean found = false;

                  while(rs.next()) {
                     found = true;
                     System.out.println("\nID: " + rs.getInt("student_id"));
                     System.out.println("Name: " + rs.getString("name"));
                     System.out.println("Birthday: " + String.valueOf(rs.getDate("birthday")));
                     System.out.println("Email: " + rs.getString("email"));
                  }

                  if (!found) {
                     System.out.println("\nNo students found.");
                  }
               } catch (Throwable var8) {
                  if (rs != null) {
                     try {
                        rs.close();
                     } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                     }
                  }

                  throw var8;
               }

               if (rs != null) {
                  rs.close();
               }
            } catch (Throwable var9) {
               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Throwable var6) {
                     var9.addSuppressed(var6);
                  }
               }

               throw var9;
            }

            if (stmt != null) {
               stmt.close();
            }
         } catch (Throwable var10) {
            if (conn != null) {
               try {
                  conn.close();
               } catch (Throwable var5) {
                  var10.addSuppressed(var5);
               }
            }

            throw var10;
         }

         if (conn != null) {
            conn.close();
         }
      } catch (SQLException var11) {
         System.out.println("Error listing students: " + var11.getMessage());
      }

   }

   private static void searchStudent() {
      System.out.println("\n<< Search Student >>");
      System.out.print("\nEnter student name to search: ");
      String searchName = scanner.nextLine();

      try {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "Add Your Password");

         try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE UPPER(name) = UPPER(?)");

            try {
               stmt.setString(1, searchName);
               ResultSet rs = stmt.executeQuery();
               boolean found = false;

               while(rs.next()) {
                  found = true;
                  System.out.println("\nID: " + rs.getInt("student_id"));
                  System.out.println("Name: " + rs.getString("name"));
                  System.out.println("Birthday: " + String.valueOf(rs.getDate("birthday")));
                  System.out.println("Email: " + rs.getString("email"));
               }

               if (!found) {
                  System.out.println("\nNo student found with that name.");
               }
            } catch (Throwable var7) {
               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (stmt != null) {
               stmt.close();
            }
         } catch (Throwable var8) {
            if (conn != null) {
               try {
                  conn.close();
               } catch (Throwable var5) {
                  var8.addSuppressed(var5);
               }
            }

            throw var8;
         }

         if (conn != null) {
            conn.close();
         }
      } catch (SQLException var9) {
         System.out.println("\nError searching for student: " + var9.getMessage());
      }

   }

   private static void updateStudent() {
      System.out.println("\n<< Update Student >>");
      int studentId = false;

      int studentId;
      while(true) {
         System.out.print("\nEnter Student ID to Update: ");

         try {
            studentId = Integer.parseInt(scanner.nextLine().trim());
            break;
         } catch (NumberFormatException var21) {
            System.out.println("Please enter a valid numeric student ID.");
         }
      }

      try {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "Add Your Password");

         try {
            PreparedStatement stmtSelect = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?");

            try {
               PreparedStatement stmtUpdate = conn.prepareStatement("UPDATE students SET name = ?, birthday = ?, email = ? WHERE student_id = ?");

               try {
                  stmtSelect.setInt(1, studentId);
                  ResultSet rs = stmtSelect.executeQuery();
                  if (rs.next()) {
                     String currentName = rs.getString("name");
                     LocalDate currentBirthday = rs.getDate("birthday").toLocalDate();
                     String currentEmail = rs.getString("email");
                     String newName = promptForName(currentName);
                     LocalDate newBirthday = promptForBirthday(currentBirthday);
                     String newEmail = promptForEmail(currentEmail);
                     stmtUpdate.setString(1, newName);
                     stmtUpdate.setDate(2, Date.valueOf(newBirthday));
                     stmtUpdate.setString(3, newEmail);
                     stmtUpdate.setInt(4, studentId);
                     int affectedRows = stmtUpdate.executeUpdate();
                     if (affectedRows > 0) {
                        System.out.println("\nStudent with ID " + studentId + " updated successfully!");
                        Iterator var12 = students.iterator();

                        while(var12.hasNext()) {
                           Student student = (Student)var12.next();
                           if (student.getStudentId() == studentId) {
                              student.setName(newName);
                              student.setBirthday(newBirthday);
                              student.setEmail(newEmail);
                              break;
                           }
                        }
                     } else {
                        System.out.println("\nNo student found with that ID.");
                     }
                  } else {
                     System.out.println("\nNo student found with that ID.");
                  }
               } catch (Throwable var17) {
                  if (stmtUpdate != null) {
                     try {
                        stmtUpdate.close();
                     } catch (Throwable var16) {
                        var17.addSuppressed(var16);
                     }
                  }

                  throw var17;
               }

               if (stmtUpdate != null) {
                  stmtUpdate.close();
               }
            } catch (Throwable var18) {
               if (stmtSelect != null) {
                  try {
                     stmtSelect.close();
                  } catch (Throwable var15) {
                     var18.addSuppressed(var15);
                  }
               }

               throw var18;
            }

            if (stmtSelect != null) {
               stmtSelect.close();
            }
         } catch (Throwable var19) {
            if (conn != null) {
               try {
                  conn.close();
               } catch (Throwable var14) {
                  var19.addSuppressed(var14);
               }
            }

            throw var19;
         }

         if (conn != null) {
            conn.close();
         }
      } catch (SQLException var20) {
         System.out.println("\nError updating student: " + var20.getMessage());
      }

   }

   private static String promptForName(String currentName) {
      while(true) {
         System.out.print("\nEnter New Name (Current: " + currentName + "): ");
         String newName = scanner.nextLine().trim();
         if (newName.isEmpty()) {
            System.out.println("\nName cannot be empty. Please enter a valid name.");
         } else {
            if (newName.matches("[a-zA-Z ]+")) {
               return newName;
            }

            System.out.println("\nInvalid name. Name can only contain letters and spaces.");
         }
      }
   }

   private static LocalDate promptForBirthday(LocalDate currentBirthday) {
      LocalDate newBirthday = currentBirthday;

      while(true) {
         System.out.print("\nEnter New Birthday (YYYY-MM-DD) (Current: " + String.valueOf(currentBirthday) + "): ");
         String newBirthdayStr = scanner.nextLine().trim();
         if (newBirthdayStr.isEmpty()) {
            break;
         }

         try {
            newBirthday = LocalDate.parse(newBirthdayStr);
            break;
         } catch (Exception var4) {
            System.out.println("\nInvalid date format. Please enter the birthday in YYYY-MM-DD format.");
         }
      }

      return newBirthday;
   }

   private static String promptForEmail(String currentEmail) {
      while(true) {
         System.out.print("\nEnter New Email (Current: " + currentEmail + "): ");
         String newEmail = scanner.nextLine().trim();
         if (newEmail.isEmpty() || newEmail.contains("@") && newEmail.contains(".")) {
            return newEmail;
         }

         System.out.println("\nInvalid email format. Please enter a valid email address.");
      }
   }

   private static void deleteStudent() {
      System.out.println("\n<< Delete Student >>");
      System.out.print("\nEnter Student ID to Delete: ");

      int studentId;
      try {
         studentId = scanner.nextInt();
      } catch (InputMismatchException var7) {
         System.out.println("\nPlease enter a valid numeric student ID.");
         scanner.nextLine();
         return;
      }

      scanner.nextLine();

      try {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "Add Your Password");

         try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE student_id = ?");

            try {
               stmt.setInt(1, studentId);
               int affectedRows = stmt.executeUpdate();
               if (affectedRows > 0) {
                  System.out.println("\nStudent with ID " + studentId + " deleted successfully!");
                  students.removeIf((s) -> {
                     return s.getStudentId() == studentId;
                  });
               } else {
                  System.out.println("\nNo student found with that ID.");
               }
            } catch (Throwable var8) {
               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Throwable var6) {
                     var8.addSuppressed(var6);
                  }
               }

               throw var8;
            }

            if (stmt != null) {
               stmt.close();
            }
         } catch (Throwable var9) {
            if (conn != null) {
               try {
                  conn.close();
               } catch (Throwable var5) {
                  var9.addSuppressed(var5);
               }
            }

            throw var9;
         }

         if (conn != null) {
            conn.close();
         }
      } catch (SQLException var10) {
         System.out.println("\nError deleting student: " + var10.getMessage());
      }

   }

   static {
      scanner = new Scanner(System.in);
   }
}
