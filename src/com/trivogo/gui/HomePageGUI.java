package com.trivogo.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageGUI {
    private  JFrame frame1;
    private JPanel parentPanel;
    private JPanel homePanel;
    private JPanel newBookingPanel;
    private JPanel previousBookingsPanel;
    private JLabel logoLabel;
    private JButton newBookingButton;
    private JButton oldBookingsButton;
    private JButton waitlistButton;
    private JPanel waitlistPanel;
    private JLabel logo;
    private JLabel locationLabel;
    private JComboBox locationBox;
    private JLabel checkinLabel;
    private JTextField checkinField;
    private JLabel checkoutLabel;
    private JTextField checkoutField;
    private JLabel peopleLabel;
    private JSpinner peopleSpinner;
    private JLabel roomsLabel;
    private JSpinner roomsSpinner;
    private JButton homeButton;
    private JButton searchButton;
    private JButton button3;

    public HomePageGUI() {
        frame1 = new JFrame();
        frame1.add(parentPanel);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.setSize(500,500);
        frame1.setVisible(true);
        newBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(newBookingPanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        oldBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(previousBookingsPanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        waitlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(waitlistPanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.removeAll();
                parentPanel.add(homePanel);
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
    }

    public static void main(String args[]) {
        HomePageGUI c1 = new HomePageGUI();
    }
}
