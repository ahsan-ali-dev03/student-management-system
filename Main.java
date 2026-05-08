import java.sql.*;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Update Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    deleteStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    searchStudent();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void addStudent() {

        try {

            Connection con = DBConnection.getConnection();

            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Roll No: ");
            int roll = sc.nextInt();

            System.out.print("Enter Marks: ");
            int marks = sc.nextInt();

            String query = "INSERT INTO students(name, roll, marks) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, roll);
            ps.setInt(3, marks);

            ps.executeUpdate();

            System.out.println("Student Added Successfully");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void viewStudents() {

        try {

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM students";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            System.out.println("\n--- Student List ---");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("roll") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("marks")
                );
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deleteStudent() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll No to Delete: ");
            int roll = sc.nextInt();

            String query = "DELETE FROM students WHERE roll=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, roll);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Deleted");
            } else {
                System.out.println("Student Not Found");
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void updateStudent() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll No to Update: ");
            int roll = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Marks: ");
            int marks = sc.nextInt();

            String query = "UPDATE students SET name=?, marks=? WHERE roll=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, marks);
            ps.setInt(3, roll);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Updated");
            } else {
                System.out.println("Student Not Found");
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void searchStudent() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll No to Search: ");
            int roll = sc.nextInt();

            String query = "SELECT * FROM students WHERE roll=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, roll);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nStudent Found:");
                System.out.println(
                        rs.getInt("roll") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("marks")
                );

            } else {

                System.out.println("Student Not Found");
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}