package hu.indicium.dev.payment.application.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import lombok.Data;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "method"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateTransferTransactionCommand.class, name = "transfer")
})
@Data
public abstract class UpdateTransactionCommand {
    private UUID paymentId;

    private UUID transactionId;

    private TransactionStatus transactionStatus;
}
