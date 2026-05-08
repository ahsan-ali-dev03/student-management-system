import javax.swing.*;
import java.awt.event.*;

public class Login {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Login System");

        JLabel title = new JLabel("Student Management Login");
        title.setBounds(100, 30, 250, 30);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 100, 100, 30);

        JTextField userField = new JTextField();
        userField.setBounds(150, 100, 150, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 160, 100, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 160, 150, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(130, 230, 120, 40);

        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String username = userField.getText();

                String password = new String(passField.getPassword());

                if (username.equals("admin") && password.equals("1234")) {

                    JOptionPane.showMessageDialog(frame, "Login Successful");

                    frame.dispose();

                    GUI.main(null);

                } else {

                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                }
            }
        });

        frame.add(title);

        frame.add(userLabel);
        frame.add(userField);

        frame.add(passLabel);
        frame.add(passField);

        frame.add(loginButton);

        frame.setSize(400, 350);

        frame.setLayout(null);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}