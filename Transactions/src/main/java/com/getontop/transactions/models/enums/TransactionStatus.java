package com.getontop.transactions.models.enums;

public enum TransactionStatus {

    //create a string return for each enum value
    //return string value for each enum value

    IN_PROGRESS("IN_PROGRESS"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public String toString() {
        return name().toLowerCase();
    }

    public static TransactionStatus fromString(String value) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

    public static TransactionStatus fromValue(int value) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.ordinal() == value) {
                return status;
            }
        }
        return null;
    }
}
