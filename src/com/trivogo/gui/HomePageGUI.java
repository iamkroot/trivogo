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
import java.util.Date;
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
    private JButton reviewsButton;
    private JButton roomButton;
    private JPanel roomsPanel;
    private JTable roomsTable;
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
    private JButton prevBookingButton;
    private JLabel nameHotelLabel;
    private JLabel typeRoomLabel;
    private JLabel numRoomsLabel;
    private JLabel dateInLabel;
    private JLabel dateOutLabel;
    private JLabel idBookingLabel;
    private JLabel statusBookingLabel;
    private JTable bookingsTable;
    private JButton cancelButton;
    private JButton modifyButton;
    private JPanel cancelPanel;
    private JPanel modifyPanel;
    private JLabel dueLabel;
    private JLabel paymentDueLabel;
    private JLabel cardNumLabel;
    private JTextField cardNumField;
    private JLabel dateExpLabel;
    private JTextField dateExpField;
    private JLabel cvvLabel;
    private JTextField cvvField;
    private JButton confirmButton;
    private JButton backPrevButton;
    private JLabel newInDateLabel;
    private JTextField textField1;
    private JLabel newOutDateLabel;
    private JTextField textField2;
    private JButton confirmModButton;
    private JButton button2;
    private JLabel selectedHotelLabel;
    private JLabel payableAmountLabel;
    //private JOptionPane verificationStatus;
    DatePickerSettings inDateSettings;
    DatePickerSettings outDateSettings;
    SpinnerNumberModel peopleSpinnerNumberModel;
    SpinnerNumberModel roomSpinnerNumberModel;
    DefaultTableModel hotelModel;
    DefaultTableModel roomModel;
    DefaultTableModel bookingModel;
    String location;
    List<Hotel> hotels;
    List<Booking> bookings;
    Hotel hotel;
    HotelRoom stdRoom;
    HotelRoom deluxeRoom;
    String verificationNumber;
    Booking booking;
    SearchParameters params;
    int payableAmount;

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
                if (bookingsTable.getRowCount() > 0) {
                    for (int i = bookingsTable.getRowCount() - 1; i > -1; i--) {
                        bookingModel.removeRow(i);
                    }
                }
                switchToPanel(previousBookingsPanel);
                bookings = BookingDAO.getUserBookings(user);

                for (Booking booking : bookings) {
                    bookingModel.addRow(new Object[]{String.valueOf(booking.getBookingID()),booking.getHotel().getName(),
                            booking.getCheckInDate().toString(),booking.getCheckOutDate().toString(),booking.getRoom().getType(),
                            String.valueOf(booking.getNumOfRooms()),booking.getStatus()});
                }
                for(int i=0; i<bookingsTable.getRowCount(); i++) {
                    bookingsTable.setRowHeight(i, 30);
                }

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

               switchToPanel(homePanel);
            }
        });
        searchHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(newBookingPanel);
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
        roomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(hotelTable.getSelectedRow() != -1) {
                    hotel = hotels.get(hotelTable.getSelectedRow());
                }
                payableAmount = 0;
                selectedHotelLabel.setText("Hotel : " + hotel.getName());
                payableAmountLabel.setText("Total Payable Amount : Rs " + payableAmount);
                switchToPanel(roomsPanel);
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
                switchToPanel(hotelPanel);
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
                        if(booking.getStatus().equals("PENDING")) {
                            booking.setStatus("CONFIRMED");
                        }
                        booking.setBookingID(BookingDAO.addBooking(booking));
                        JOptionPane.showMessageDialog(null,"Verification Successfull");
                        switchToPanel(summaryPanel);
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
                        booking.setBookingID(BookingDAO.addBooking(booking));
                        JOptionPane.showMessageDialog(null,"Verification Successfull");
                        switchToPanel(summaryPanel);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid Pan Card Number");
                    }
                }
                nameHotelLabel.setText(booking.getHotel().getName());
                typeRoomLabel.setText(booking.getRoom().getType());
                numRoomsLabel.setText(String.valueOf(booking.getNumOfRooms()));
                dateInLabel.setText(booking.getCheckInDate().toString());
                dateOutLabel.setText(booking.getCheckOutDate().toString());
                idBookingLabel.setText(String.valueOf(booking.getBookingID()));
                statusBookingLabel.setText(booking.getStatus());
                panButton.setSelected(false);
                panField.setText("");
                adhaarButton.setSelected(false);
                adhaarField.setText("");

            }
        });
        backRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(roomsPanel);
                panButton.setSelected(false);
                adhaarButton.setSelected(false);
                payableAmount = 0;
            }
        });
        reviewsButton.addActionListener(new ActionListener() {
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
                    reviewsButton.setEnabled(true);
                    roomButton.setEnabled(true);
                }
                else {
                    reviewsButton.setEnabled(false);
                    roomButton.setEnabled(false);
                }
            }
        });
        roomsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(roomsTable.getSelectedRow() != -1) {
                    confirmBookingButton.setEnabled(true);
                    int x;
                    if(roomsTable.getSelectedRow() == 0){
                        x = hotel.getStdRoom().getRate();
                    } else {
                        x = hotel.getDexRoom().getRate();
                    }
                    x *= params.getNumRooms();
                    payableAmount = x*diffInDate(params.getCheckInDate(), params.getCheckOutDate());

                    payableAmountLabel.setText("Total Payable Amount : Rs " + payableAmount);
                }
                else {
                    confirmBookingButton.setEnabled(false);
                }
            }
        });
        confirmBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                booking = new Booking(hotel, user, roomsTable.getSelectedRow() == 0 ? hotel.getRoomInfo("standard") : hotel.getRoomInfo("deluxe"), params, "PENDING" );
                if (params.getNumRooms() <= com.trivogo.dao.BookingDAO.getNumAvailableRooms(booking)) {
                    switchToPanel(verificationPanel);
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
                        switchToPanel(verificationPanel);
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
        prevBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bookingsTable.getRowCount() > 0) {
                    for (int i = bookingsTable.getRowCount() - 1; i > -1; i--) {
                        bookingModel.removeRow(i);
                    }
                }
                bookings = BookingDAO.getUserBookings(user);
                for (Booking booking : bookings) {
                    bookingModel.addRow(new Object[]{String.valueOf(booking.getBookingID()),booking.getHotel().getName(),
                            booking.getCheckInDate().toString(),booking.getCheckOutDate().toString(),booking.getRoom().getType(),
                            String.valueOf(booking.getNumOfRooms()),booking.getStatus()});
                }
                for(int i=0; i<bookingsTable.getRowCount(); i++) {
                    bookingsTable.setRowHeight(i, 30);
                }
                switchToPanel(previousBookingsPanel);
            }
        });
        backPrevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(previousBookingsPanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bookingsTable.getSelectedRow() != -1) {
                    booking = bookings.get(bookingsTable.getSelectedRow());
                }
                int y =  diffInDate(params.getCheckInDate(), params.getCheckOutDate())
                java.util.Date today = new Date();
                int price = booking.getNumOfRooms()*booking.getRoom().getRate()*y*(-1);
                if( y == -1 || y == -2 ) {
                    cardNumLabel.setVisible(true);
                    cardNumField.setVisible(true);
                    dateExpLabel.setVisible(true);
                    dateExpField.setVisible(true);
                    cvvField.setVisible(true);
                    cvvLabel.setVisible(true);
                    confirmButton.setText("Confirm and Pay");
                    paymentDueLabel.setText("Rs. "+ String.valueOf(price*0.5));
                }else {
                    cardNumLabel.setVisible(false);
                    cardNumField.setVisible(false);
                    dateExpLabel.setVisible(false);
                    dateExpField.setVisible(false);
                    cvvField.setVisible(false);
                    cvvLabel.setVisible(false);
                    confirmButton.setText("Confirm");
                    paymentDueLabel.setText("Rs. 0.0");
                }
                switchToPanel(cancelPanel);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(booking.getPayableAmount() != 0) {
                    JOptionPane.showMessageDialog(null,"Payment Successful. Booking cancelled.");
                    booking.setStatus("CANCELLED");
                    // TODO: 21/11/18 load data into table again 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Booking successfully cancelled.");
                    booking.setStatus("CANCELLED");
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

        bookingsTable = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingModel = new DefaultTableModel();
        bookingModel.addColumn("Booking Id");
        bookingModel.addColumn("Hotel Name");
        bookingModel.addColumn("Check-in date");
        bookingModel.addColumn("Check-out date");
        bookingModel.addColumn("Room Type");
        bookingModel.addColumn("Number of Rooms");
        bookingModel.addColumn("Status");


        bookingsTable.setModel(bookingModel);

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

    private void switchToPanel(JPanel jpanel){
        cardPanel.removeAll();
        cardPanel.add(jpanel);
        cardPanel.repaint();
        cardPanel.revalidate();
    }

    private int diffInDate(Date d1, Date d2){
        Long difference =  (d1.getTime()-d2.getTime())/86400000;
        Integer y = difference.intValue();
    }
}
