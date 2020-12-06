package hu.indicium.dev.payment.infrastructure.persistency;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import hu.indicium.dev.payment.infrastructure.persistency.jpa.PaymentJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentRepository;

    @Override
    public PaymentId nextIdentity() {
        UUID uuid = UUID.randomUUID();
        PaymentId paymentId = PaymentId.fromId(uuid);
        if (paymentRepository.existsByPaymentId(paymentId)) {
            return nextIdentity();
        }
        return paymentId;
    }

    @Override
    public Payment getPaymentById(PaymentId paymentId) {
        return paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Payment %s not found.", paymentId.getId().toString())));
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Collection<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
