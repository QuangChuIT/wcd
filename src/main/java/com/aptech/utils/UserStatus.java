package com.aptech.utils;

public enum UserStatus {
    LOCK(1),
    ACTIVE(0);
    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
