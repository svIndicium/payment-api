package hu.indicium.dev.payment.infrastructure.payment;

import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {

    private String transactionId;

    private String description;

    private String redirectUrl;

    private Double amount;

    public PaymentDetails(TransactionId transactionId, String description, String redirectUrl, Double amount) {
        this.transactionId = transactionId.getId().toString();
        this.description = description;
        this.redirectUrl = redirectUrl;
        this.amount = amount;
    }
}
