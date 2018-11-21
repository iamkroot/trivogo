package com.trivogo.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.trivogo.dao.BookingDAO;
import com.trivogo.dao.HotelDAO;
import com.trivogo.dao.ReviewDAO;
import com.trivogo.models.*;
import com.trivogo.utils.DateUtil;
import com.trivogo.utils.Regex;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Date;
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
    private JButton confirmCancelButton;
    private JButton backPrevButton;
    private JLabel newInDateLabel;
    private DatePicker newInDatePicker;
    private JLabel newOutDateLabel;
    private DatePicker newOutDatePicker;
    private JButton confirmModButton;
    private JButton backModifyButton;
    private JLabel selectedHotelLabel;
    private JLabel payableAmountLabel;
    private JPanel viewReviewPanel;
    private JButton addReviewButton;
    private JPanel writeReviewPanel;
    private JSpinner locationSpinner;
    private JLabel hotelLocLabel;
    private JLabel hotelRoomLabel;
    private JSpinner roomReviewSpinner;
    private JLabel serviceLabel;
    private JSpinner serviceSpinner;
    private JLabel cleanlinessLabel;
    private JSpinner cleanlinessSpinner;
    private JLabel valueMoneyLabel;
    private JSpinner valForMonSpinner;
    private JLabel overallReviewLabel;
    private JTextArea overallReviewField;
    private JButton submitReviewButton;
    private JTable reviewTable;
    private JButton backHotelsButton;
    private JCheckBox waitlistCheckBox;

    private User user;
    DatePickerSettings inDateSettings, newInDateSettings;
    DatePickerSettings outDateSettings, newOutDateSettings;
    SpinnerNumberModel peopleSpinnerNumberModel;
    SpinnerNumberModel roomSpinnerNumberModel;
    SpinnerNumberModel locationSpinnerNumberModel;
    SpinnerNumberModel roomReviewSpinnerNumberModel;
    SpinnerNumberModel servicesSpinnerNumberModel;
    SpinnerNumberModel cleanSpinnerNumberModel;
    SpinnerNumberModel valForMoneySpinnerNumberModel;
    DefaultTableModel hotelModel;
    DefaultTableModel roomModel;
    DefaultTableModel bookingModel;
    DefaultTableModel reviewModel;
    String location;
    List<Hotel> hotels;
    List<Booking> bookings;
    List<Review> reviews;
    Hotel hotel;
    HotelRoom stdRoom;
    HotelRoom deluxeRoom;
    String verificationNumber;
    Booking booking;
    Booking selectedBooking;
    SearchParameters params;
    int payableAmount;
    int rowIndex;

    public HomePageGUI(User loggedInUser) {
        user = loggedInUser;
        frame1 = new JFrame();
        frame1.add(parentPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(700, 700);
        frame1.setVisible(true);


        previousBookingButton.addActionListener(e -> {
            addReviewButton.setEnabled(false);
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
        });

        inDatePicker.addPropertyChangeListener(propertyChangeEvent -> {
            if(inDatePicker.getDate().isAfter(outDatePicker.getDate()) || inDatePicker.getDate().isEqual(outDatePicker.getDate())) {
                outDatePicker.setDate(inDatePicker.getDate().plusDays(1));
            }
            outDateSettings.setDateRangeLimits(inDatePicker.getDate().plusDays(1), inDatePicker.getDate().plusYears(1));
        });
        searchButton.addActionListener(e -> {
            location = (String) locationBox.getSelectedItem();
            LocalDate inDate = inDatePicker.getDate();
            LocalDate outDate = outDatePicker.getDate();
            int noOfPeople = (int) peopleSpinner.getValue();
            int noOfRoom = (int) roomsSpinner.getValue();
            params = new SearchParameters(location, java.sql.Date.valueOf(inDate), java.sql.Date.valueOf(outDate), noOfPeople, noOfRoom);
            hotels = HotelDAO.getHotelsByLocation(location);

            for (Hotel hotel : hotels) {
                hotelModel.addRow(new Object[]{hotel.getName(),hotel.getRating()});
            }
            for(int i=0; i<hotelTable.getRowCount(); i++) {
                hotelTable.setRowHeight(i, 50);
            }

           switchToPanel(hotelPanel);
        });
        searchHotelButton.addActionListener(e -> {
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

        });
        roomButton.addActionListener(actionEvent -> {

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
        });
        adhaarButton.addActionListener(e -> {
            adhaarLabel.setVisible(true);
            adhaarField.setVisible(true);
            panButton.setSelected(false);
            panField.setVisible(false);
            panLabel.setVisible(false);
        });
        panButton.addActionListener(e -> {
            panField.setVisible(true);
            panLabel.setVisible(true);
            adhaarButton.setSelected(false);
            adhaarLabel.setVisible(false);
            adhaarField.setVisible(false);
        });
        backButton.addActionListener(e -> {
            switchToPanel(hotelPanel);
            if (roomsTable.getRowCount() > 0) {
                for (int i = roomsTable.getRowCount() - 1; i > -1; i--) {
                    roomModel.removeRow(i);
                }
            }
        });
        verifyButton.addActionListener(e -> {
            if(adhaarButton.isSelected()) {
                verificationNumber = adhaarField.getText();
                if(verificationNumber.length() == 12) {
                    if(booking.getStatus().equals("PENDING")) {
                        booking.setStatus("CONFIRMED");
                    }
                    booking.setBookingID(BookingDAO.addBooking(booking));
                    booking.setPayableAmount(payableAmount);
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
                    booking.setPayableAmount(payableAmount);
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

        });
        backRoomButton.addActionListener(e -> {
            switchToPanel(roomsPanel);
            panButton.setSelected(false);
            adhaarButton.setSelected(false);
            payableAmount = 0;
        });
        reviewsButton.addActionListener(actionEvent -> {
            if(hotelTable.getSelectedRow() != -1) {
                hotel = hotels.get(hotelTable.getSelectedRow());
            }
        });
        hotelTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            if(hotelTable.getSelectedRow() != -1) {
                reviewsButton.setEnabled(true);
                roomButton.setEnabled(true);
            }
            else {
                reviewsButton.setEnabled(false);
                roomButton.setEnabled(false);
            }
        });
        roomsTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            if(roomsTable.getSelectedRow() != -1) {
                confirmBookingButton.setEnabled(true);
                int x;
                if(roomsTable.getSelectedRow() == 0){
                    x = hotel.getStdRoom().getRate();
                } else {
                    x = hotel.getDexRoom().getRate();
                }
                x *= params.getNumRooms();
                payableAmount = x*DateUtil.calcDatesDiff(params.getCheckInDate(), params.getCheckOutDate());
                payableAmountLabel.setText("Total Payable Amount : Rs " + payableAmount);
            }
            else {
                confirmBookingButton.setEnabled(false);
            }
        });
        bookingsTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            if(bookingsTable.getSelectedRow() != -1) {
                Booking selectedBooking = bookings.get(bookingsTable.getSelectedRow());
                if(selectedBooking.getStatus().equals("CANCELLED") || selectedBooking.isLapsed()) {
                    modifyButton.setEnabled(false);
                    cancelButton.setEnabled(false);
                    if(selectedBooking.isLapsed()) {
                        if(selectedBooking.getReview() == null) {
                            addReviewButton.setEnabled(false);
                        }
                        else {
                            addReviewButton.setEnabled(true);
                        }
                    }
                }
                else {
                    modifyButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    addReviewButton.setEnabled(false);
                }
            }
            else {
                modifyButton.setEnabled(false);
                cancelButton.setEnabled(false);
                addReviewButton.setEnabled(false);
            }
        });
        confirmBookingButton.addActionListener(actionEvent -> {
            booking = new Booking(hotel, user, roomsTable.getSelectedRow() == 0 ? hotel.getRoomInfo("Standard") : hotel.getRoomInfo("Deluxe"), params, "PENDING" );
            if (params.getNumRooms() <= BookingDAO.getNumAvailableRooms(booking)) {
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
            }
        });
        prevBookingButton.addActionListener(e -> {
            addReviewButton.setEnabled(false);
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
        });
        backPrevButton.addActionListener(e -> switchToPanel(previousBookingsPanel));
        cancelButton.addActionListener(e -> {
            if(bookingsTable.getSelectedRow() != -1) {
                booking = bookings.get(bookingsTable.getSelectedRow());
                rowIndex = bookingsTable.getSelectedRow();
            }
            int y =  DateUtil.calcDatesDiff(booking.getCheckInDate(), booking.getCheckOutDate());
            Date today = new Date();
            int price = booking.getNumOfRooms()*booking.getRoom().getRate()*y;
            int t = DateUtil.calcDatesDiff(today, booking.getCheckInDate());
            if( t == 1 || t == 2 ) {
                cardNumLabel.setVisible(true);
                cardNumField.setVisible(true);
                dateExpLabel.setVisible(true);
                dateExpField.setVisible(true);
                cvvField.setVisible(true);
                cvvLabel.setVisible(true);
                confirmCancelButton.setText("Confirm and Pay");
                paymentDueLabel.setText("Rs. "+ String.valueOf(price*0.5));
            }else {
                cardNumLabel.setVisible(false);
                cardNumField.setVisible(false);
                dateExpLabel.setVisible(false);
                dateExpField.setVisible(false);
                cvvField.setVisible(false);
                cvvLabel.setVisible(false);
                confirmCancelButton.setText("Confirm");
                paymentDueLabel.setText("Rs. 0.0");
            }
            switchToPanel(cancelPanel);
        });
        confirmCancelButton.addActionListener(e -> {
            if(booking.getPayableAmount() != 0) {
                if((cvvField.toString().length() == 3) && Regex.validate_date(dateExpField.toString()) && (cardNumField.toString().length() == 10)) {
                    JOptionPane.showMessageDialog(null, "Payment Successful. Booking cancelled.");
                    booking = bookings.get(rowIndex);
                    cancelBooking();
                    bookingsTable.setValueAt("CANCELLED",rowIndex,6);
                    switchToPanel(previousBookingsPanel);

                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Booking successfully cancelled.");
                booking = bookings.get(rowIndex);
                cancelBooking();
                bookingsTable.setValueAt("CANCELLED",rowIndex,6);
                switchToPanel(previousBookingsPanel);
            }
        });
        backModifyButton.addActionListener(e -> switchToPanel(previousBookingsPanel));
        addReviewButton.addActionListener(e -> {
            selectedBooking = bookings.get(bookingsTable.getSelectedRow());
            switchToPanel(writeReviewPanel);

        });
        submitReviewButton.addActionListener(e -> {
            String reviewDescription = overallReviewField.getText();
            int location = (int) locationSpinner.getValue();
            int rooms = (int) roomReviewSpinner.getValue();
            int services = (int) serviceSpinner.getValue();
            int clean = (int) cleanlinessSpinner.getValue();
            int valForMoney = (int) valForMonSpinner.getValue();
            Review review = new Review(selectedBooking.getHotel(),selectedBooking.getUser(),reviewDescription, location, rooms, services,
                    clean,valForMoney);
            ReviewDAO.addReview(selectedBooking.getBookingID(),review);
            JOptionPane.showMessageDialog(null,"Review successfully added.");
            switchToPanel(previousBookingsPanel);
        });
        reviewsButton.addActionListener(e -> {
            if (reviewTable.getRowCount() > 0) {
                for (int i = reviewTable.getRowCount() - 1; i > -1; i--) {
                    reviewModel.removeRow(i);
                }
            }

            reviews = ReviewDAO.getHotelReviews(hotels.get(hotelTable.getSelectedRow()));

            for (Review review : reviews) {
                reviewModel.addRow(new Object[]{review.getHotel().getName(),review.getUser().getUsername(),review.getReviewDescription(),
                review.getParamLocation(),review.getParamRoom(),review.getParamService(),review.getParamClean(),review.getParamValueForMoney()});
            }
            for(int i=0; i<reviewTable.getRowCount(); i++) {
                reviewTable.setRowHeight(i, 50);
            }
            switchToPanel(viewReviewPanel);
        });
        backHotelsButton.addActionListener(e -> switchToPanel(hotelPanel));
        modifyButton.addActionListener(actionEvent -> {
            if(bookingsTable.getSelectedRow() != -1) {
                selectedBooking = bookings.get(bookingsTable.getSelectedRow());
                rowIndex = bookingsTable.getSelectedRow();
            }
            LocalDate oldCheckInDate = DateUtil.toLocalDate(selectedBooking.getCheckInDate()), oldCheckOutDate = DateUtil.toLocalDate(selectedBooking.getCheckOutDate());
            newInDatePicker.setDate(oldCheckInDate);
            newInDateSettings.setDateRangeLimits(oldCheckInDate.minusDays(5), oldCheckInDate.plusDays(5));
            newOutDatePicker.setDate(oldCheckOutDate);
            newOutDateSettings.setDateRangeLimits(newInDatePicker.getDate().plusDays(1), oldCheckOutDate.plusDays(5));
            switchToPanel(modifyPanel);
        });
        confirmModButton.addActionListener(actionEvent -> {
            Booking tempBooking = new Booking(selectedBooking);
            Date newCheckInDate = DateUtil.toDate(newInDatePicker.getDate()), newCheckOutDate = DateUtil.toDate(newOutDatePicker.getDate());
            tempBooking.setCheckInDate(newCheckInDate);
            tempBooking.setCheckOutDate(newCheckOutDate);
            BookingDAO.updateStatus(selectedBooking.getBookingID(), "PENDING MODIFY");
            if(tempBooking.getNumOfRooms() <= BookingDAO.getNumAvailableRooms(tempBooking)){
                tempBooking.setStatus("CONFIRMED");
                BookingDAO.updateBooking(selectedBooking.getBookingID(), newCheckInDate, newCheckOutDate);
                selectedBooking = tempBooking;
                nameHotelLabel.setText(selectedBooking.getHotel().getName());
                typeRoomLabel.setText(selectedBooking.getRoom().getType());
                numRoomsLabel.setText(String.valueOf(selectedBooking.getNumOfRooms()));
                dateInLabel.setText(selectedBooking.getCheckInDate().toString());
                dateOutLabel.setText(selectedBooking.getCheckOutDate().toString());
                idBookingLabel.setText(String.valueOf(selectedBooking.getBookingID()));
                statusBookingLabel.setText(selectedBooking.getStatus());
                switchToPanel(summaryPanel);
            }
            else{
                int a = JOptionPane.showConfirmDialog(frame1, "There are no rooms available for your duration of stay. But we can add you to the waiting list." +
                        " Would you like to enroll in the waiting list for this room", "No Rooms Available", JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.YES_OPTION){
                    tempBooking.setStatus("WAITLIST PENDING");
                    selectedBooking = tempBooking;
                    BookingDAO.updateBooking(selectedBooking.getBookingID(), newCheckInDate, newCheckOutDate);
                    nameHotelLabel.setText(selectedBooking.getHotel().getName());
                    typeRoomLabel.setText(selectedBooking.getRoom().getType());
                    numRoomsLabel.setText(String.valueOf(selectedBooking.getNumOfRooms()));
                    dateInLabel.setText(selectedBooking.getCheckInDate().toString());
                    dateOutLabel.setText(selectedBooking.getCheckOutDate().toString());
                    idBookingLabel.setText(String.valueOf(selectedBooking.getBookingID()));
                    statusBookingLabel.setText(selectedBooking.getStatus());
                    switchToPanel(summaryPanel);
                }
            }
            BookingDAO.updateStatus(selectedBooking.getBookingID(), selectedBooking.getStatus());
        });
        waitlistCheckBox.addActionListener(e -> {
            TableRowSorter<DefaultTableModel>tr = new TableRowSorter<>(bookingModel);
            bookingsTable.setRowSorter(tr);
            if(waitlistCheckBox.isSelected()) {
                tr.setRowFilter(RowFilter.regexFilter("WAITLIST",6));
                for(int i=0; i<bookingsTable.getRowCount(); i++) {
                    bookingsTable.setRowHeight(i, 30);
                }
            }
            else {
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
            }
        });
        newInDatePicker.addPropertyChangeListener(propertyChangeEvent -> {
            if(newInDatePicker.getDate().isAfter(newOutDatePicker.getDate()) || newInDatePicker.getDate().isEqual(newOutDatePicker.getDate())) {
                newOutDatePicker.setDate(newInDatePicker.getDate().plusDays(1));
            }
            LocalDate rightLimit = null;
            if(selectedBooking != null)
                rightLimit = DateUtil.toLocalDate(selectedBooking.getCheckOutDate()).plusDays(5);
            newOutDateSettings.setDateRangeLimits(newInDatePicker.getDate().plusDays(1), rightLimit);
        });
    }

    private void switchToPanel(JPanel jpanel){
        cardPanel.removeAll();
        cardPanel.add(jpanel);
        cardPanel.repaint();
        cardPanel.revalidate();
    }

    private void cancelBooking() {
        booking.setStatus("CANCELLED");
        BookingDAO.cancelBooking(booking.getBookingID());
        updateWaitlistedBookings(booking);
    }

    private void updateWaitlistedBookings(Booking booking) {
        List<Booking> waitlistedBookings = BookingDAO.getWaitlistPendingBookings(booking.getHotel(), booking.getRoom());
        for(Booking waitlisted: waitlistedBookings) {
            if(waitlisted.getNumOfRooms() <= BookingDAO.getNumAvailableRooms(waitlisted)){
                BookingDAO.confirmWaitlistedBooking(waitlisted.getBookingID());
            }
        }
    }

    private void createUIComponents() {
        hotelTable = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
            }
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
            }
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

        reviewTable = new JTable() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reviewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reviewModel = new DefaultTableModel();
        reviewModel.addColumn("Hotel Name");
        reviewModel.addColumn("User Name");
        reviewModel.addColumn("Review");
        reviewModel.addColumn("Hotel Location");
        reviewModel.addColumn("Hotel Rooms");
        reviewModel.addColumn("Hotel Services");
        reviewModel.addColumn("Hotel Cleanliness");
        reviewModel.addColumn("Value for Money");

        reviewTable.setModel(reviewModel);

        Vector<String> allLocation = new Vector<>(com.trivogo.dao.HotelDAO.getAllLocations());
        locationBox = new JComboBox(allLocation);

        final LocalDate today = LocalDate.now();
        inDateSettings = DateUtil.createDefaultSettings();
        inDatePicker = new DatePicker(inDateSettings);
        inDateSettings.setDateRangeLimits(today.plusDays(1), today.plusYears(1));
        inDatePicker.setDate(today.plusDays(1));

        outDateSettings = DateUtil.createDefaultSettings();
        outDatePicker = new DatePicker(outDateSettings);
        outDatePicker.setDate(inDatePicker.getDate().plusDays(1));
        outDateSettings.setDateRangeLimits(inDatePicker.getDate().plusDays(1), today.plusYears(1));

        newInDateSettings = DateUtil.createDefaultSettings();
        newInDatePicker = new DatePicker(newInDateSettings);

        newOutDateSettings = DateUtil.createDefaultSettings();
        newOutDatePicker = new DatePicker(newOutDateSettings);

        peopleSpinnerNumberModel = new SpinnerNumberModel(1, 1, 100, 1);
        peopleSpinner = new JSpinner(peopleSpinnerNumberModel);

        roomSpinnerNumberModel = new SpinnerNumberModel(1, 1, 100, 1);
        roomsSpinner = new JSpinner(roomSpinnerNumberModel);

        locationSpinnerNumberModel = new SpinnerNumberModel(1,1,10,1);
        locationSpinner = new JSpinner(locationSpinnerNumberModel);

        roomReviewSpinnerNumberModel = new SpinnerNumberModel(1,1,10,1);
        roomReviewSpinner = new JSpinner(roomReviewSpinnerNumberModel);

        servicesSpinnerNumberModel = new SpinnerNumberModel(1,1,10,1);
        serviceSpinner = new JSpinner(servicesSpinnerNumberModel);

        cleanSpinnerNumberModel = new SpinnerNumberModel(1,1,10,1);
        cleanlinessSpinner = new JSpinner(cleanSpinnerNumberModel);

        valForMoneySpinnerNumberModel = new SpinnerNumberModel(1,1,10,1);
        valForMonSpinner = new JSpinner(valForMoneySpinnerNumberModel);
    }
}
