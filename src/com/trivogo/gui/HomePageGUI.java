package com.trivogo.gui;

import com.trivogo.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class HomePageGUI {
    private User user;
    private JFrame frame1;
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
    private JTable hotelTable;

    public HomePageGUI(User user) {
        this.user = user;
        frame1 = new JFrame();
        frame1.add(parentPanel);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.setSize(700,700);
        frame1.setVisible(true);
        frame1.setAlwaysOnTop(true);

        searchHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(newBookingPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        previousBookingButton.addActionListener(new ActionListener() {
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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Vector <String> allLocation = new Vector<>(com.trivogo.dao.HotelDAO.getAllLocations());
        locationBox = new JComboBox(allLocation);
    }
}
