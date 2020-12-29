package hu.indicium.dev.payment.infrastructure.persistency;

import hu.indicium.dev.payment.domain.model.transaction.IDealTransaction;
import hu.indicium.dev.payment.domain.model.transaction.IDealTransactionRepository;
import hu.indicium.dev.payment.infrastructure.persistency.jpa.IDealTransactionJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
@AllArgsConstructor
public class IDealTransactionRepositoryImpl implements IDealTransactionRepository {

    private final IDealTransactionJpaRepository iDealTransactionJpaRepository;

    @Override
    public IDealTransaction findIDealTransactionByExternalId(String externalId) {
        return iDealTransactionJpaRepository.findByExternalId(externalId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find iDeal transaction with id %s", externalId)));
    }
}
