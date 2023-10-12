package com.getontop.clients.user.models.requestObjects;

public record UserRegistrationRequest(
        String name,
        String surName,
        String nationalId,
        String bankName,
        String accountNumber,
        String routingNumber) {

}