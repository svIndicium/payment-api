package hu.indicium.dev.payment.application.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "method"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewCashTransactionCommand.class, name = "cash"),
        @JsonSubTypes.Type(value = NewTransferTransactionCommand.class, name = "transfer")
})
@Data
public abstract class NewTransactionCommand {
    private UUID paymentId;

    private String method;

    private Double amount;
}
