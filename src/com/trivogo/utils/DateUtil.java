package com.trivogo.utils;

import com.trivogo.models.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static Date readFromDB(String DBDate) {
        Date date = null;
        try {
            date = (new SimpleDateFormat("yyyy-MM-dd")).parse(DBDate);
        } catch (ParseException e) {
            System.err.println("Error while reading dob from database.");
        }
        return date;
    }
    public static String convertToDBFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static LocalDate toLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
