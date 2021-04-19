package hu.indicium.dev.payment.application.query;

import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import hu.indicium.dev.payment.domain.model.transaction.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TransactionQueryServiceImpl implements TransactionQueryService {

    private final TransactionRepository transactionRepository;

    @Override
    @PostAuthorize("hasPermission('admin:payment') || (hasPermission('read:payment') && userIdEquals(returnObject.payment.memberId.authId))")
    public Transaction getTransactionById(TransactionId transactionId) {
        return transactionRepository.getTransactionById(transactionId);
    }

    @Override
    @PreAuthorize("hasPermission('admin:payment')")
    public Collection<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    @Override
    @PostAuthorize("hasPermission('admin:payment') || (hasPermission('read:payment') && userIdEquals(returnObject[0].payment.memberId.authId))")
    public Collection<Transaction> getAllTransactionsByPaymentId(PaymentId paymentId) {
        return transactionRepository.getTransactionsByPaymentId(paymentId);
    }
}
