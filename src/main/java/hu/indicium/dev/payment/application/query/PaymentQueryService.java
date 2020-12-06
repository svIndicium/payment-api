package hu.indicium.dev.payment.application.query;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;

import java.util.Collection;

public interface PaymentQueryService {
    Payment getPaymentById(PaymentId paymentId);

    Collection<Payment> getAllPayments();
}
