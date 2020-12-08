package hu.indicium.dev.payment.application.commands;

import lombok.Data;

@Data
public class NewPaymentCommand {
    private String description;

    private double amount;

    private String authId;
}
