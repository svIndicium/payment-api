package hu.indicium.dev.payment.domain.model.payment;

import hu.indicium.dev.payment.domain.model.member.MemberId;

import java.util.Collection;

public interface PaymentRepository {
    PaymentId nextIdentity();

    Payment getPaymentById(PaymentId paymentId);

    Payment save(Payment payment);

    Collection<Payment> getAllPayments();

    Collection<Payment> getPaymentsWithOpenTransferTransactions();

    Collection<Payment> getPaymentsByMemberId(MemberId memberId);
}
