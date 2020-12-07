package hu.indicium.dev.payment.application.query;

import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;

import java.util.Collection;

public interface TransactionQueryService {
    Transaction getTransactionById(TransactionId transactionId);

    Collection<Transaction> getAllTransactions();

    Collection<Transaction> getAllTransactionsByPaymentId(PaymentId paymentId);
}
