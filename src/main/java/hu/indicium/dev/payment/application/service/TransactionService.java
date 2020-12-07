package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.NewTransactionCommand;
import hu.indicium.dev.payment.application.commands.UpdateTransactionCommand;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;

public interface TransactionService {
    TransactionId createTransaction(NewTransactionCommand newTransactionCommand);

    void updateTransaction(UpdateTransactionCommand updateTransactionCommand);
}
