package com.trivogo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static final Pattern EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate_email(String email) {
        Matcher matcher = EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    private static final Pattern DATE_REGEX =
            Pattern.compile("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$");

    public static boolean validate_date(String date) {
        Matcher matcher = DATE_REGEX .matcher(date);
        return matcher.find();
    }

}
