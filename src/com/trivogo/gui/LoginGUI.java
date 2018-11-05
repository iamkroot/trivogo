package com.trivogo.gui;
import javax.swing.*;
import java.awt.*;

class LoginGUI {
    JFrame frame;
    JLabel trivogoLogo, signInLabel, signUpLabel, usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton signInButton;
    LoginGUI() {
        frame = new JFrame();
        trivogoLogo = new JLabel(new ImageIcon("src/com/trivogo/resources/trivogo.png"));
        signInLabel = new JLabel("SIGN IN");
        signUpLabel = new JLabel("CREATE NEW ACCOUNT");
        usernameLabel = new JLabel("Username -");
        passwordLabel = new JLabel("Password -");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        signInButton = new JButton("Sign In");
        trivogoLogo.setBounds(400,50,200,100);
        signInLabel.setBounds(250,150,100,100);
        signUpLabel.setBounds(700,150,300,100);
        usernameLabel.setBounds(150,250,100,50);
        passwordLabel.setBounds(150,350,100,50);
        usernameField.setBounds(150,300,300,25);
        passwordField.setBounds(150,400,300,25);
        signInButton.setBounds(200,450,200,50);
        Font font = signInLabel.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, 18);
        signInLabel.setFont(boldFont);
        signUpLabel.setFont(boldFont);
        frame.add(trivogoLogo);
        frame.add(signInLabel);
        frame.add(signUpLabel);
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(usernameField);
        frame.add(passwordField);
        frame.add(signInButton);
        frame.setSize(1000,1000);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
