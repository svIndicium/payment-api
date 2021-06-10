package hu.indicium.dev.payment.infrastructure.payment;

public interface PaymentProvider {
    PaymentObject createPayment(PaymentDetails paymentDetails);

    PaymentObject getPaymentByExternalId(String externalTransactionId);
}
