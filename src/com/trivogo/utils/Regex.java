package com.trivogo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static final Pattern EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate_email(String email) {
        Matcher matcher = EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private static final Pattern DATE_REGEX =
            Pattern.compile("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$");

    public static boolean validate_date(String date) {
        Matcher matcher = DATE_REGEX.matcher(date);
        return matcher.find();
    }

    private static final Pattern EXPDATE_REGEX =
            Pattern.compile("^(0[1-9]|1[012])[- /.](19|20)\\d\\d$");

    public static boolean validate_expdate(String date) {
        Matcher matcher = EXPDATE_REGEX.matcher(date);
        return matcher.find();
    }

    private static final Pattern ADHAAR_REGEX =
            Pattern.compile("^\\d{16}$");

    public static boolean validate_adhaar(String date) {
        Matcher matcher = ADHAAR_REGEX.matcher(date);
        return matcher.find();
    }

    private static final Pattern PAN_REGEX =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    public static boolean validate_pan(String date) {
        Matcher matcher = PAN_REGEX.matcher(date);
        return matcher.find();
    }

    private static final Pattern CARD_REGEX =
            Pattern.compile("^4\\d{12}$");

    public static boolean validate_card(String date) {
        Matcher matcher = CARD_REGEX.matcher(date);
        return matcher.find();
    }
}
