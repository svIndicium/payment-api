package hu.indicium.dev.payment.infrastructure.web.dto.mapper;

import hu.indicium.dev.payment.domain.model.transaction.CashTransaction;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.infrastructure.web.dto.CashTransactionDto;
import hu.indicium.dev.payment.infrastructure.web.dto.TransactionDto;

public class TransactionMapper {
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction instanceof CashTransaction) {
            return new CashTransactionDto((CashTransaction) transaction);
        }
        return new TransactionDto(transaction);
    }
}
