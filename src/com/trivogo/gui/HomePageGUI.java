package com.trivogo.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.trivogo.dao.BookingDAO;
import com.trivogo.dao.HotelDAO;
import com.trivogo.models.*;

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
    private JPanel verificationPanel;
    private JLabel verificationLabel;
    private JRadioButton adhaarButton;
    private JRadioButton panButton;
    private JLabel adhaarLabel;
    private JLabel panLabel;
    private JTextField adhaarField;
    private JTextField panField;
    private JButton verifyButton;
    private JButton backRoomButton;
    private JPanel summaryPanel;
    private JLabel summaryLabel;
    private JLabel hotelLabel;
    private JLabel roomLabel;
    private JLabel noRoomLabel;
    private JLabel inDateLabel;
    private JLabel outDateLabel;
    private JLabel bookingIDLabel;
    private JLabel bookingStatusLabel;
    //private JOptionPane verificationStatus;
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
    String verificationNumber;
    Booking booking;
    SearchParameters params;

    public HomePageGUI(User user) {
        this.user = user;
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
                params = new SearchParameters(location, java.sql.Date.valueOf(inDate), java.sql.Date.valueOf(outDate), noOfPeople, noOfRoom);
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
                confirmBookingButton.setEnabled(false);
            }
        });
        adhaarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adhaarLabel.setVisible(true);
                adhaarField.setVisible(true);
                panButton.setSelected(false);
                panField.setVisible(false);
                panLabel.setVisible(false);
            }
        });
        panButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panField.setVisible(true);
                panLabel.setVisible(true);
                adhaarButton.setSelected(false);
                adhaarLabel.setVisible(false);
                adhaarField.setVisible(false);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(hotelPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
                if (roomsTable.getRowCount() > 0) {
                    for (int i = roomsTable.getRowCount() - 1; i > -1; i--) {
                        roomModel.removeRow(i);
                    }
                }
            }
        });
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(adhaarButton.isSelected()) {
                    verificationNumber = adhaarField.getText();
                    if(verificationNumber.length() == 12) {
                        if(booking.getStatus().equals("PENDING"))
                            booking.setStatus("CONFIRMED");
                        BookingDAO.addBooking(booking);
                        JOptionPane.showMessageDialog(null,"Verification Successfull");
                        cardPanel.removeAll();
                        cardPanel.add(summaryPanel);
                        cardPanel.repaint();
                        cardPanel.revalidate();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid Adhaar Card Number");
                    }
                }
                else if(panButton.isSelected()) {
                    verificationNumber = panField.getText();
                    if(verificationNumber.length() == 10) {
                        if(booking.getStatus().equals("PENDING"))
                            booking.setStatus("CONFIRMED");
                        BookingDAO.addBooking(booking);
                        JOptionPane.showMessageDialog(null,"Verification Successfull");
                        cardPanel.removeAll();
                        cardPanel.add(summaryPanel);
                        cardPanel.repaint();
                        cardPanel.revalidate();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid Pan Card Number");
                    }
                }

            }
        });
        backRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(roomsPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        viewReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(hotelTable.getSelectedRow() != -1) {
                    hotel = hotels.get(hotelTable.getSelectedRow());
                }
            }
        });
        hotelTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(hotelTable.getSelectedRow() != -1) {
                    viewReviewButton.setEnabled(true);
                    viewRoomsButton.setEnabled(true);
                }
                else {
                    viewReviewButton.setEnabled(false);
                    viewRoomsButton.setEnabled(false);
                }
            }
        });
        roomsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(hotelTable.getSelectedRow() != -1) {
                    confirmBookingButton.setEnabled(true);
                }
                else {
                    confirmBookingButton.setEnabled(false);
                }
            }
        });
        confirmBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                booking = new Booking(hotel, user, hotel.getRoomInfo("deluxe"), params, "PENDING" );
                if (params.getNumRooms() <= com.trivogo.dao.BookingDAO.getNumAvailableRooms(booking)) {
                    cardPanel.removeAll();
                    cardPanel.add(verificationPanel);
                    cardPanel.repaint();
                    cardPanel.revalidate();
                    panLabel.setVisible(false);
                    panField.setVisible(false);
                    adhaarField.setVisible(false);
                    adhaarLabel.setVisible(false);
                }
                else {
                    int a = JOptionPane.showConfirmDialog(frame1, "There are no rooms available for your duration of stay. But we can add you to the waiting list." +
                            " Would you like to enroll in the waiting list for this room", "No Rooms Available", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.YES_OPTION){
                        booking.setStatus("WAITLIST PENDING");
                        cardPanel.removeAll();
                        cardPanel.add(verificationPanel);
                        cardPanel.repaint();
                        cardPanel.revalidate();
                        panLabel.setVisible(false);
                        panField.setVisible(false);
                        adhaarField.setVisible(false);
                        adhaarLabel.setVisible(false);
                    }
                    else{

                    }
                }
            }
        });
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
