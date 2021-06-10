package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.model.payment.PaymentId;

import java.util.Collection;

public interface TransactionRepository {
    TransactionId nextIdentity();

    Transaction getTransactionById(TransactionId transactionId);

    Collection<Transaction> getTransactionsByPaymentId(PaymentId paymentId);

    Transaction save(Transaction transaction);

    Collection<Transaction> getAllTransactions();
}
