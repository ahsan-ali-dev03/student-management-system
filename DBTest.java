import java.sql.*;

public class DBTest {

    public static void main(String[] args) {

        try {

            String url = "jdbc:mysql://localhost:3306/student_db";
            String user = "root";
            String password = "root";

            Connection con = DriverManager.getConnection(url, user, password);

            System.out.println("Database Connected Successfully");

        } catch (Exception e) {

            System.out.println(e);

        }
    }
}