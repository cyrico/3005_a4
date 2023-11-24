import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;


public class DatabaseOperations {

    private final String url = "jdbc:postgresql://localhost:5432/test_java";
    private final String user = "postgres";
    private final String password = "postgres";

    // Add a user
    public void addStudent(String first_name, String last_name, String email, Date date) {
        String SQL = "INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES(?,?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, date);

            pstmt.executeUpdate();
            System.out.println("User added successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Update user's email based on name
    public void modifyStudentEmail(int id, String newEmail) {
        String SQL = "UPDATE students SET email=? WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
    
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Student email updated");
    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //delete user based on student id
    public void deleteUser(int Id) {
        String SQL = "DELETE FROM students WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, Id);
            pstmt.executeUpdate();
            System.out.println("User deleted!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //print the students table and all its values
    public void getAllStudents() {
    String SQL = "SELECT * FROM students";
    
    try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        ResultSet rs = pstmt.executeQuery()) {
    
        while (rs.next()) {
            int studentId = rs.getInt("student_id");
            String fName = rs.getString("first_name");
            String lName = rs.getString("last_name");
            String email = rs.getString("email");
            Date enrollment = rs.getDate("enrollment_date");
    
            System.out.println("Student ID: " + studentId);
            System.out.println("First Name: " + fName);
            System.out.println("Last Name: " + lName);
            System.out.println("Email: " + email);
            System.out.println("Enrollment Date: " + enrollment);
            System.out.println("---------------------------------");
            }
    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
     }
}
    

public static void main(String[] args) {
        DatabaseOperations dbOps = new DatabaseOperations();
        Scanner scanner = new Scanner(System.in);
        //calls the get students function
        System.out.println("Would you like to see the students table? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            dbOps.getAllStudents();
        }
      
      // Add a student
      System.out.println("Would you like to add a student? (yes/no)");
      if (scanner.nextLine().equalsIgnoreCase("yes")) {

        String fname, lname, email, strDate;

        //do while loops to make sure its not empty input
        do {
            System.out.print("First name of student: ");
            fname = scanner.nextLine();
        }
        while(fname.isEmpty() || fname.trim().isEmpty()); 

        do { 
            System.out.print("Last name of student: ");
            lname = scanner.nextLine();
        }
        while (lname.isEmpty() || lname.trim().isEmpty());
        do {
            System.out.print("Email of student: ");
            email = scanner.nextLine();
        }
        while(email.isEmpty() || email.trim().isEmpty());

        System.out.print("Date of enrollement in year/month/day format (yyyy-mm-dd):");
        strDate = scanner.nextLine();   
        //changes the string date in an SQL date variable
        Date date = Date.valueOf(strDate);    
        //add the student  
        dbOps.addStudent(fname, lname, email, date);
      }
  
      // Modify students email
        System.out.println("Would you like to modify a user's email? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
        String email;
        System.out.print("ID of student with the email to be modified: ");
        int id = scanner.nextInt();
        System.out.print("New email of student: ");
    
        do {   
            email = scanner.nextLine();                
        }
        while(email.isEmpty() || email.trim().isEmpty());
        dbOps.modifyStudentEmail(id, email);
      }

      // Delete user
      System.out.println("Would you like to delete a user? (yes/no)");
      if (scanner.nextLine().equalsIgnoreCase("yes")) {
         
        System.out.print("ID of student to be deleted: ");
        int id = scanner.nextInt();
        dbOps.deleteUser(id);
      }

      dbOps.getAllStudents();
      scanner.close();
  }
}