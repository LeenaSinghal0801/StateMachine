package com.docusign.app;

public enum Temperature {
    HOT,
    COLD;

    public static Temperature fromString(String s) {
        return Temperature.valueOf(s.trim().toUpperCase());
    }
}
