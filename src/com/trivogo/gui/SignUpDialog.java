package com.trivogo.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.trivogo.dao.UserDAO;
import com.trivogo.models.User;
import com.trivogo.utils.Hasher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import static com.trivogo.utils.Regex.validate_date;
import static com.trivogo.utils.Regex.validate_email;

public class SignUpDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel SignUpForm;
    private JTextField usernameField;
    private JTextField fullnameField;
    private JLabel usernameLabel;
    private JLabel fullNameLabel;
    private JTextField emailField;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JTextArea addressField;
    private JPasswordField passwordField1;
    private JLabel passwordLabel;
    private JPanel Form;
    private JPanel Buttons;
    private JLabel logo;
    private DatePicker dobPicker;

    public SignUpDialog() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Sign Up");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String name = fullnameField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String date = dobPicker.getDateStringOrEmptyString();
        char[] password = passwordField1.getPassword();
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
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        final LocalDate today = LocalDate.now();
        DatePickerSettings dobSettings = new DatePickerSettings();
        dobPicker = new DatePicker(dobSettings);
        dobPicker.setDate(today.minusYears(18));
        dobSettings.setAllowEmptyDates(false);
        dobSettings.setDateRangeLimits(null, today.minusYears(18));
        dobSettings.setFormatForDatesCommonEra("dd/MM/yyyy");


    }

}
