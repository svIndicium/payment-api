package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.transaction.IDealTransaction;
import lombok.Getter;

import java.util.Date;

@Getter
public class IDealTransactionDto extends TransactionDto {

    private final String externalId;

    private final String paymentProvider;

    private final Date expiresAt;

    private final String checkoutUrl;

    private final String webhookUrl;

    private final String redirectUrl;

    public IDealTransactionDto(IDealTransaction transaction) {
        super(transaction);
        this.externalId = transaction.getExternalId();
        this.paymentProvider = transaction.getPaymentProvider();
        this.expiresAt = transaction.getExpiresAt();
        this.checkoutUrl = transaction.getCheckoutUrl();
        this.webhookUrl = transaction.getWebhookUrl();
        this.redirectUrl = transaction.getRedirectUrl();
    }
}
