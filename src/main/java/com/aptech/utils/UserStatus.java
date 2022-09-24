package com.aptech.utils;

public enum UserStatus {
    LOCK(0),
    ACTIVE(1);
    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
