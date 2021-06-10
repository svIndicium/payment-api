package hu.indicium.dev.payment.infrastructure.payment.mollie;

import be.woutschoovaerts.mollie.data.payment.PaymentResponse;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import hu.indicium.dev.payment.infrastructure.payment.PaymentObject;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


public class MolliePayment implements PaymentObject {
    private final String externalId;

    private final TransactionId transactionId;

    private final TransactionStatus transactionStatus;

    private Date expiresAt;

    private String checkoutUrl;

    private final String webhookUrl;

    private final String redirectUrl;

    private Date paidAt;

    public MolliePayment(PaymentResponse paymentResponse) {
        this.externalId = paymentResponse.getId();
        Map<String, Object> metadata = paymentResponse.getMetadata();
        String id = (String) metadata.get("transactionId");
        UUID uuid = UUID.fromString(id);
        this.transactionId = TransactionId.fromId(uuid);
        this.transactionStatus = getTransactionStatus(paymentResponse.getStatus());
        if (this.transactionStatus.equals(TransactionStatus.PENDING) || this.transactionStatus.equals(TransactionStatus.OPEN)) {
            this.expiresAt = paymentResponse.getExpiresAt();
            this.checkoutUrl = paymentResponse.getLinks().getCheckout().getHref();
        } else if (this.transactionStatus.equals(TransactionStatus.PAID)) {
            this.paidAt = paymentResponse.getPaidAt().orElse(new Date());
        }
        this.webhookUrl = paymentResponse.getWebhookUrl().orElse("not found");
        this.redirectUrl = paymentResponse.getRedirectUrl();
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public TransactionId getTransactionId() {
        return transactionId;
    }

    @Override
    public String getPaymentProvider() {
        return "mollie";
    }

    @Override
    public TransactionStatus getStatus() {
        return transactionStatus;
    }

    @Override
    public Date getExpiresAt() {
        return expiresAt;
    }

    @Override
    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    @Override
    public String getWebhookUrl() {
        return webhookUrl;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public Date getPaidAt() {
        return paidAt;
    }

    private TransactionStatus getTransactionStatus(String status) {
        switch (status) {
            case "open":
                return TransactionStatus.OPEN;
            case "pending":
                return TransactionStatus.PENDING;
            case "paid":
                return TransactionStatus.PAID;
            case "expired":
                return TransactionStatus.EXPIRED;
            case "failed":
                return TransactionStatus.FAILED;
            case "canceled":
                return TransactionStatus.CANCELED;
        }
        throw new IllegalStateException("Unknown state");
    }
}
