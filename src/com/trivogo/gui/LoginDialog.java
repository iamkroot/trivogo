package com.trivogo.gui;

import com.trivogo.dao.UserDAO;
import com.trivogo.models.User;
import com.trivogo.utils.Hasher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JPanel Icon;

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SignUpDialog dialog = new SignUpDialog();
                dialog.pack();
                dialog.setVisible(true);
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
        String username = usernameField.getText();
        char[] password = passwordField1.getPassword();
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
                HomePageGUI.main(null);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Username and/or Password field were empty",
                    "Empty Field(s)",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoginDialog dialog = new LoginDialog();
        dialog.setTitle("Login");
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
