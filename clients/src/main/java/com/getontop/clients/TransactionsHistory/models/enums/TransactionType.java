package com.getontop.clients.TransactionsHistory.models.enums;

public enum TransactionType {
    TopUp("TopUp"),
    Withdraw("Withdraw"),
    Transfer("Transfer");

    private final String value;

    TransactionType(String value) {
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
