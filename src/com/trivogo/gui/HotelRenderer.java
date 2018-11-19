package com.trivogo.gui;

import com.trivogo.dao.HotelDAO;
import com.trivogo.models.Hotel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRenderer extends JLabel implements ListCellRenderer<Hotel> {
    String location;
    HotelRenderer(String location)
    {
        this.location = location;
    }
    public Component getListCellRendererComponent (JList<? extends Hotel> list, Hotel hotel, int index, boolean isSelected, boolean cellHasFocus) {

    }

}
