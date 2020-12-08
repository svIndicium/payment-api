package hu.indicium.dev.payment.infrastructure.payment;

import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;

import java.util.Date;

public interface PaymentObject {
    String getExternalId();

    TransactionId getTransactionId();

    String getPaymentProvider();

    TransactionStatus getStatus();

    Date getExpiresAt();

    String getCheckoutUrl();

    String getWebhookUrl();

    String getRedirectUrl();
}
