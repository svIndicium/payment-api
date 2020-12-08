package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.transaction.IDealTransaction;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import lombok.Getter;

import java.util.Date;

@Getter
public class IDealTransactionDto extends TransactionDto {

    private String externalId;

    private String paymentProvider;

    private Date expiresAt;

    private String checkoutUrl;

    private String webhookUrl;

    private String redirectUrl;

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
