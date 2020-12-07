package hu.indicium.dev.payment.infrastructure.persistency;

import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import hu.indicium.dev.payment.domain.model.transaction.TransactionRepository;
import hu.indicium.dev.payment.infrastructure.persistency.jpa.TransactionJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionRepository;

    @Override
    public TransactionId nextIdentity() {
        UUID uuid = UUID.randomUUID();
        TransactionId transactionId = TransactionId.fromId(uuid);
        if (transactionRepository.existsByTransactionId(transactionId)) {
            return nextIdentity();
        }
        return transactionId;
    }

    @Override
    public Transaction getTransactionById(TransactionId transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Transaction %s not found.", transactionId.getId().toString())));
    }

    @Override
    public Collection<Transaction> getTransactionsByPaymentId(PaymentId paymentId) {
        return transactionRepository.findByPaymentPaymentId(paymentId);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Collection<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
