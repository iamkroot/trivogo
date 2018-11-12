package com.trivogo.gui;

import javax.swing.*;
import javax.swing.text.DateFormatter;

public class SideBarPanel {
    private JLabel searchHotelLabel;
    private JPanel Form;
    private JPanel SearchForm;
    private JLabel locataionLabel;
    private JTextField locationField;
    private JLabel fromLabel;
    private JTextField fromField;
    private JLabel toLabel;
    private JTextField toField;
    private JLabel noOfRoomsLabel;
    private JTextField noOfRoomsField;
    private JLabel noOfPeopleLabel;
    private JTextField noOfPeopleField;
    private JPanel Buttons;
    private JButton buttonSearch;
    private JPanel SideBarPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("SideBarPanel");
        frame.setContentPane(new SideBarPanel().SideBarPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
