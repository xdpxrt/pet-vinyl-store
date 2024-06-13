package ru.xdpxrt.vinyl.cons;

import java.time.format.DateTimeFormatter;

public class Config {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final String ORDERS_TOPIC = "orders-topic";
    public static final String BIRTHDAY_TOPIC = "birthday-topic";
}