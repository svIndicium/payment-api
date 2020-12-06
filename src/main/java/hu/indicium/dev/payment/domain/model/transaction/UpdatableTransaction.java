package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;

public interface UpdatableTransaction<T extends BaseDetails> {
    void updateTransaction(T updatedTransactionInfo);
}
