package hu.indicium.dev.payment.infrastructure.persistency.jpa;

import hu.indicium.dev.payment.domain.model.member.MemberId;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
    boolean existsByPaymentId(PaymentId paymentId);

    Optional<Payment> findByPaymentId(PaymentId paymentId);

    Collection<Payment> getPaymentsByMemberId(MemberId memberId);
}
