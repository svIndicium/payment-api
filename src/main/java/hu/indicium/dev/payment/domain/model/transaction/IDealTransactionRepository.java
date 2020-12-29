package hu.indicium.dev.payment.domain.model.transaction;

public interface IDealTransactionRepository {
    IDealTransaction findIDealTransactionByExternalId(String externalId);
}
