package hu.indicium.dev.payment.domain.model.transaction;

import java.util.Collection;

public interface TransactionRepository {
    TransactionId nextIdentity();

    Transaction getTransactionById(TransactionId TransactionId);

    Transaction save(Transaction Transaction);

    Collection<Transaction> getAllTransactions();
}
