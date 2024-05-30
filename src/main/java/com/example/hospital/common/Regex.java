package com.example.hospital.common;

public class Regex {
    public static final String EMAIL = "[A-Za-z0-9+_.-]+@(.+)$"; // check dinh dang email // chuoi dinh dang email
    public static final String PHONE_NUMBER = "^(0[1-9]|84[1-9])[0-9]{8}$"; // check dinh dang so dien thoai
    public static final String DATE_TIME_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3]):([0-5][0-9])$"; // dd/mm/yyyy hh:mm

    public static boolean isValid(String input, String type) {
        if (type.equals("email")) {
            return input.matches(EMAIL);
        } else if (type.equals("phone")) {
            return input.matches(PHONE_NUMBER);
        } else if (type.equals("datetime")) {
            return input.matches(DATE_TIME_PATTERN);
        }
        return false;
    }
}

