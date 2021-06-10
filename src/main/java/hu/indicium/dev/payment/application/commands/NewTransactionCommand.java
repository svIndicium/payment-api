package hu.indicium.dev.payment.application.commands;

import lombok.Data;

import java.util.UUID;

@Data
public class NewTransactionCommand {
    private UUID paymentId;

    private String method;

    private Double amount;

    private String description;

    private String redirectUrl;
}
