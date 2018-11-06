package com.trivogo.gui;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}
