package com.trivogo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;

import com.trivogo.dao.UserDAO;
import com.trivogo.models.User;
import com.trivogo.utils.Hasher;

import static com.trivogo.utils.Regex.validate_date;
import static com.trivogo.utils.Regex.validate_email;

class LoginGUI implements ActionListener {
    JFrame frame;
    JLabel trivogoLogo, usernameLabelPanel1, passwordLabelPanel1, usernameLabelPanel2, passwordLabelPanel2, nameLabel, addressLabel, emailLabel, dateLabel;
    JTextField usernameFieldPanel1, usernameFieldPanel2, nameField, addressField, emailField, dateField;
    JPasswordField passwordFieldPanel1, passwordFieldPanel2;
    JPanel signInPanel, signUpPanel;
    JButton signInButton, signUpButton;

    LoginGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trivogoLogo = new JLabel(new ImageIcon("src/com/trivogo/resources/trivogo.png"));
        usernameLabelPanel1 = new JLabel("Username -");
        passwordLabelPanel1 = new JLabel("Password -");
        usernameLabelPanel2 = new JLabel("Username -");
        passwordLabelPanel2 = new JLabel("Password -");
        nameLabel = new JLabel("Full Name-");
        addressLabel = new JLabel("Address-");
        emailLabel = new JLabel("Email id-");
        dateLabel = new JLabel("Date of Birth-");
        usernameFieldPanel1 = new JTextField();
        usernameFieldPanel2 = new JTextField();
        nameField = new JTextField();
        addressField = new JTextField();
        emailField = new JTextField();
        dateField = new JTextField();
        passwordFieldPanel1 = new JPasswordField();
        passwordFieldPanel2 = new JPasswordField();
        signInButton = new JButton("Sign In");
        signUpButton = new JButton("Sign Up");
        signInPanel = new JPanel();
        signUpPanel = new JPanel();
        JTabbedPane loginPane = new JTabbedPane();
        loginPane.add("Sign In", signInPanel);
        loginPane.add("Sign Up", signUpPanel);

        dateField.setText("dd/mm/yy");

        loginPane.setBounds(150, 150, 400, 200);
        trivogoLogo.setBounds(250, 50, 200, 100);

        signInButton.addActionListener(this);
        signUpButton.addActionListener(this);

        frame.add(loginPane);
        frame.add(trivogoLogo);
        signInPanel.add(usernameLabelPanel1);
        signInPanel.add(usernameFieldPanel1);
        signInPanel.add(passwordLabelPanel1);
        signInPanel.add(passwordFieldPanel1);
        signInPanel.add(signInButton);

        signUpPanel.add(nameLabel);
        signUpPanel.add(nameField);
        signUpPanel.add(addressLabel);
        signUpPanel.add(addressField);
        signUpPanel.add(emailLabel);
        signUpPanel.add(emailField);
        signUpPanel.add(dateLabel);
        signUpPanel.add(dateField);
        signUpPanel.add(usernameLabelPanel2);
        signUpPanel.add(usernameFieldPanel2);
        signUpPanel.add(passwordLabelPanel2);
        signUpPanel.add(passwordFieldPanel2);
        signUpPanel.add(signUpButton);
        signInPanel.setLayout(new GridLayout(5, 2));
        signUpPanel.setLayout(new GridLayout(7, 2));
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signInButton) {
            String username = usernameFieldPanel1.getText();
            char[] password = passwordFieldPanel1.getPassword();
            if (username.length() != 0 && password[0] != '\0') {
                User user = UserDAO.getUser(username);
                if (user == null) {
                    JOptionPane.showMessageDialog(null,
                            "The Username is does not exist. Please enter correct Username.",
                            "Username Does Not Exist",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!user.getPassword().equals(Hasher.hash(String.valueOf(password)))) {
                    JOptionPane.showMessageDialog(null,
                            "Password entered is incorrect. Please try again.",
                            "Password Incorrect",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    //Go to "Find Hotels" page
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Username and/or Password field were empty",
                        "Empty Field(s)",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == signUpButton) {
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String username = usernameFieldPanel2.getText();
            String date = dateField.getText();
            char[] password = passwordFieldPanel2.getPassword();
            {
                if (name.length() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Full Name field can not be empty",
                            "Full Name Field Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (address.length() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Address field can not be empty",
                            "Address Field Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!validate_email(email)) {
                    JOptionPane.showMessageDialog(null,
                            "Email is incorrect or empty",
                            "Email Field Incorrect/Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (username.length() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Username field can not be empty",
                            "Username Field Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!validate_date(date)) {
                    JOptionPane.showMessageDialog(null,
                            "Date of Birth must be in dd/mm/yyyy format with the leading zeros if necessary.",
                            "Date of Birth Invalid",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (password[0] == '\0') {
                    JOptionPane.showMessageDialog(null,
                            "Password has to be more than 0 characters",
                            "Password Field Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (UserDAO.getUser(username) != null) {
                JOptionPane.showMessageDialog(null,
                        "The Username is taken. Please try using another Username.",
                        "Username Exists",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                User new_user = new User(name, address, username, Hasher.hash(String.valueOf(password)), email, date);
                UserDAO.addUser(new_user);
                JOptionPane.showMessageDialog(null,
                        "Your Account with the given information has been created.",
                        "Account Created",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
