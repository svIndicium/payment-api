package hu.indicium.dev.payment.domain.model.payment;

import java.util.Collection;

public interface PaymentRepository {
    PaymentId nextIdentity();

    Payment getPaymentById(PaymentId paymentId);

    Payment save(Payment payment);

    Collection<Payment> getAllPayments();
}
