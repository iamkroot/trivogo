package com.trivogo.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageGUI {
    private  JFrame frame1;
    private JPanel cardPanel;
    private JPanel homePanel;
    private JPanel newBookingPanel;
    private JPanel previousBookingsPanel;
    private JButton searchHotelButton;
    private JButton oldBookingsButton;
    private JButton waitlistButton;
    private JPanel waitlistPanel;
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
    private JPanel hotelPanel;
    private JPanel parentPanel;
    private JLabel logoLabel;
    private JButton previousBookingButton;

    public HomePageGUI() {
        frame1 = new JFrame();
        frame1.add(parentPanel);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.setSize(700,700);
        frame1.setVisible(true);
        searchHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(newBookingPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        oldBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(previousBookingsPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        waitlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(waitlistPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(homePanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
    }

    public static void main(String args[]) {
        HomePageGUI c1 = new HomePageGUI();
    }
}
