package hu.indicium.dev.payment.application.commands;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class UpdateTransferTransactionCommand extends UpdateTransactionCommand {
    private String description;

    private Date transferredAt;

    private Double paid;
}
