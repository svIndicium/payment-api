package hu.indicium.dev.payment.infrastructure.payment;

import hu.indicium.dev.payment.domain.model.transaction.TransactionId;

public interface PaymentProvider {
    PaymentObject createPayment(PaymentDetails paymentDetails);
}
