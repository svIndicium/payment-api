package hu.indicium.dev.payment.infrastructure.persistency.jpa;

import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, UUID> {
    boolean existsByTransactionId(TransactionId TransactionId);

    Optional<Transaction> findByTransactionId(TransactionId TransactionId);

    Collection<Transaction> findByPaymentPaymentId(PaymentId paymentId);
}
