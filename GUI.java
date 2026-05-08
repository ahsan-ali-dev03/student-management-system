import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GUI {

    static final String URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "root";
    static final String PASSWORD = "root";

    static JTextField nameField, rollField, marksField;

    static DefaultTableModel model;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Advanced Student Management System");

        frame.getContentPane().setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Student Management System");
        title.setBounds(170, 20, 350, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 100, 30);
        nameLabel.setForeground(Color.WHITE);

        nameField = new JTextField();
        nameField.setBounds(150, 70, 150, 30);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(50, 120, 100, 30);
        rollLabel.setForeground(Color.WHITE);

        rollField = new JTextField();
        rollField.setBounds(150, 120, 150, 30);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setBounds(50, 170, 100, 30);
        marksLabel.setForeground(Color.WHITE);

        marksField = new JTextField();
        marksField.setBounds(150, 170, 150, 30);

        JButton addButton = new JButton("Add");
        addButton.setBounds(350, 70, 120, 35);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(350, 120, 120, 35);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(350, 170, 120, 35);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(500, 70, 120, 35);

        JButton viewButton = new JButton("Refresh");
        viewButton.setBounds(500, 120, 120, 35);

        // Button styles
        JButton[] buttons = {
                addButton,
                updateButton,
                deleteButton,
                searchButton,
                viewButton
        };

        for (JButton btn : buttons) {

            btn.setBackground(Color.BLACK);

            btn.setForeground(Color.WHITE);

            btn.setFocusPainted(false);

            btn.setFont(new Font("Arial", Font.BOLD, 14));
        }

        model = new DefaultTableModel();

        JTable table = new JTable(model);

        model.addColumn("Roll No");
        model.addColumn("Name");
        model.addColumn("Marks");

        JScrollPane pane = new JScrollPane(table);

        pane.setBounds(50, 250, 570, 250);

        // ADD
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String name = nameField.getText();

                    int roll = Integer.parseInt(rollField.getText());

                    int marks = Integer.parseInt(marksField.getText());

                    String query = "INSERT INTO students(name, roll, marks) VALUES (?, ?, ?)";

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setString(1, name);
                    ps.setInt(2, roll);
                    ps.setInt(3, marks);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Student Added");

                    clearFields();

                    loadTable();

                    con.close();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(frame, ex);
                }
            }
        });

        // UPDATE
        updateButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String name = nameField.getText();

                    int roll = Integer.parseInt(rollField.getText());

                    int marks = Integer.parseInt(marksField.getText());

                    String query = "UPDATE students SET name=?, marks=? WHERE roll=?";

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setString(1, name);
                    ps.setInt(2, marks);
                    ps.setInt(3, roll);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "Student Updated");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Student Not Found");
                    }

                    clearFields();

                    loadTable();

                    con.close();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(frame, ex);
                }
            }
        });

        // DELETE
        deleteButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    int roll = Integer.parseInt(rollField.getText());

                    String query = "DELETE FROM students WHERE roll=?";

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setInt(1, roll);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "Student Deleted");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Student Not Found");
                    }

                    clearFields();

                    loadTable();

                    con.close();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(frame, ex);
                }
            }
        });

        // SEARCH
        searchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    int roll = Integer.parseInt(rollField.getText());

                    String query = "SELECT * FROM students WHERE roll=?";

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setInt(1, roll);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        nameField.setText(rs.getString("name"));

                        marksField.setText(String.valueOf(rs.getInt("marks")));

                        JOptionPane.showMessageDialog(frame, "Student Found");

                    } else {

                        JOptionPane.showMessageDialog(frame, "Student Not Found");
                    }

                    con.close();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(frame, ex);
                }
            }
        });

        // REFRESH TABLE
        viewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                loadTable();
            }
        });

        frame.add(title);

        frame.add(nameLabel);
        frame.add(nameField);

        frame.add(rollLabel);
        frame.add(rollField);

        frame.add(marksLabel);
        frame.add(marksField);

        frame.add(addButton);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(searchButton);
        frame.add(viewButton);

        frame.add(pane);

        frame.setSize(700, 600);

        frame.setLayout(null);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadTable();
    }

    static void loadTable() {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            model.setRowCount(0);

            String query = "SELECT * FROM students";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                model.addRow(new Object[] {
                        rs.getInt("roll"),
                        rs.getString("name"),
                        rs.getInt("marks")
                });
            }

            con.close();

        } catch (Exception ex) {

            System.out.println(ex);
        }
    }

    static void clearFields() {

        nameField.setText("");

        rollField.setText("");

        marksField.setText("");
    }
}