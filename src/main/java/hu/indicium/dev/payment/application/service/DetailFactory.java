package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.UpdateTransactionCommand;
import hu.indicium.dev.payment.domain.model.transaction.TransactionType;
import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.IDealDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;

public class DetailFactory {

    private DetailFactory() {
        // private no-args constructor to hide the implicit public one
    }

    public static BaseDetails createDetails(TransactionType transactionType, UpdateTransactionCommand request) {
        switch (transactionType) {
            case CASH:
                throw new IllegalStateException("Betaal methode niet aanpasbaar.");
            case IDEAL:
                return new IDealDetails(request.getTransactionStatus(), request.getTransferredAt());
            case TRANSFER:
                return new TransferDetails(request.getTransactionStatus(), request.getTransferredAt(), request.getDescription(), request.getPaid());
        }
        throw new IllegalStateException("Betaal methode niet ondersteund.");
    }
}
