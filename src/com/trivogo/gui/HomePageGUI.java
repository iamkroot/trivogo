package com.trivogo.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.trivogo.dao.HotelDAO;
import com.trivogo.models.Hotel;
import com.trivogo.models.HotelRoom;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Vector;
import java.util.List;

public class HomePageGUI {
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
    private JLabel checkoutLabel;
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
    private DatePicker inDatePicker;
    private DatePicker outDatePicker;
    private JButton viewReviewButton;
    private JButton viewRoomsButton;
    private JLabel hotelNameLabel;
    private JLabel ratingLabel;
    private JPanel roomsPanel;
    private JTable roomsTable;
    private JLabel amenitiesLabel;
    private JLabel roomTypeLabel;
    private JLabel roomRateLabel;
    private JButton confirmBookingButton;
    private JButton backButton;
    DatePickerSettings inDateSettings;
    DatePickerSettings outDateSettings;
    SpinnerNumberModel peopleSpinnerNumberModel;
    SpinnerNumberModel roomSpinnerNumberModel;
    DefaultTableModel hotelModel;
    DefaultTableModel roomModel;
    String location;
    List<Hotel> hotels;
    Hotel hotel;
    HotelRoom stdRoom;
    HotelRoom deluxeRoom;

    public HomePageGUI() {
        frame1 = new JFrame();
        frame1.add(parentPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(700, 700);
        frame1.setVisible(true);


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
        inDatePicker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                if(inDatePicker.getDate().isAfter(outDatePicker.getDate())) {
                    outDatePicker.setDate(inDatePicker.getDate().plusDays(1));
                }
                outDateSettings.setDateRangeLimits(inDatePicker.getDate().plusDays(1), inDatePicker.getDate().plusYears(1));
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                location = (String) locationBox.getSelectedItem();
                java.time.LocalDate inDate = inDatePicker.getDate();
                java.time.LocalDate outDate = outDatePicker.getDate();
                int noOfPeople = (int) peopleSpinner.getValue();
                int noOfRoom = (int) roomsSpinner.getValue();
                hotels = HotelDAO.getHotelsByLocation(location);

                for (Hotel hotel : hotels) {
                    hotelModel.addRow(new Object[]{hotel.getName(), "4 star"});
                }
                for(int i=0; i<hotelTable.getRowCount(); i++) {
                    hotelTable.setRowHeight(i, 50);
                }

                cardPanel.removeAll();
                cardPanel.add(hotelPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        searchHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(newBookingPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
                if (hotelTable.getRowCount() > 0) {
                    for (int i = hotelTable.getRowCount() - 1; i > -1; i--) {
                        hotelModel.removeRow(i);
                    }
                }

                if (roomsTable.getRowCount() > 0) {
                    for (int i = roomsTable.getRowCount() - 1; i > -1; i--) {
                        roomModel.removeRow(i);
                    }
                }

            }
        });
        viewRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(hotelTable.getSelectedRow() != -1) {
                    hotel = hotels.get(hotelTable.getSelectedRow());
                }
                cardPanel.removeAll();
                cardPanel.add(roomsPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
                deluxeRoom = hotel.getDexRoom();
                stdRoom = hotel.getStdRoom();
                roomModel.addRow(new Object[]{stdRoom.getType(),stdRoom.getAmenities(),stdRoom.getRate()});
                roomModel.addRow(new Object[]{deluxeRoom.getType(),deluxeRoom.getAmenities(),deluxeRoom.getRate()});
                for(int i=0; i<roomsTable.getRowCount(); i++) {
                    roomsTable.setRowHeight(i, 70);
                }
            }
        });
    }

    public static void main(String args[]) {
        HomePageGUI c1 = new HomePageGUI();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        hotelTable = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        hotelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hotelModel = new DefaultTableModel();
        hotelModel.addColumn("Hotel Name");
        hotelModel.addColumn("Rating");

        hotelTable.setModel(hotelModel);

        roomsTable = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        roomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomModel = new DefaultTableModel();
        roomModel.addColumn("Room Type");
        roomModel.addColumn("Amenities");
        roomModel.addColumn("Rate");

        roomsTable.setModel(roomModel);

        Vector<String> allLocation = new Vector<>(com.trivogo.dao.HotelDAO.getAllLocations());
        locationBox = new JComboBox(allLocation);

        final LocalDate today = LocalDate.now();
        inDateSettings = new DatePickerSettings();
        inDateSettings.setFormatForDatesCommonEra("d MMM yyyy");
        inDateSettings.setAllowKeyboardEditing(true);
        inDatePicker = new DatePicker(inDateSettings);
        inDateSettings.setAllowEmptyDates(false);
        inDateSettings.setAllowKeyboardEditing(true);
        inDateSettings.setDateRangeLimits(today.plusDays(1), today.plusYears(1));
        inDatePicker.setDate(today.plusDays(1));

        outDateSettings = new DatePickerSettings();
        outDateSettings.setFormatForDatesCommonEra("d MMM yyyy");
        outDateSettings.setAllowKeyboardEditing(true);
        outDatePicker = new DatePicker(outDateSettings);
        outDateSettings.setAllowEmptyDates(false);
        outDateSettings.setAllowKeyboardEditing(true);
        outDateSettings.setDateRangeLimits(inDatePicker.getDate(), today.plusYears(1));
        outDatePicker.setDate(inDatePicker.getDate().plusDays(1));

        peopleSpinnerNumberModel = new SpinnerNumberModel(1, 1, 100, 1);
        peopleSpinner = new JSpinner(peopleSpinnerNumberModel);

        roomSpinnerNumberModel = new SpinnerNumberModel(1, 1, 100, 1);
        roomsSpinner = new JSpinner(roomSpinnerNumberModel);
    }
}
