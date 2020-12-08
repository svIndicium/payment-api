package hu.indicium.dev.payment.infrastructure.web.dto.mapper;

import hu.indicium.dev.payment.domain.model.transaction.CashTransaction;
import hu.indicium.dev.payment.domain.model.transaction.IDealTransaction;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransferTransaction;
import hu.indicium.dev.payment.infrastructure.web.dto.CashTransactionDto;
import hu.indicium.dev.payment.infrastructure.web.dto.IDealTransactionDto;
import hu.indicium.dev.payment.infrastructure.web.dto.TransactionDto;
import hu.indicium.dev.payment.infrastructure.web.dto.TransferTransactionDto;

public class TransactionMapper {
    public static TransactionDto toDto(Transaction transaction) {
        switch (transaction.getType()) {
            case CASH:
                return new CashTransactionDto((CashTransaction) transaction);
            case TRANSFER:
                return new TransferTransactionDto((TransferTransaction) transaction);
            case IDEAL:
                return new IDealTransactionDto((IDealTransaction) transaction);
            default:
                return new TransactionDto(transaction);
        }
    }
}
