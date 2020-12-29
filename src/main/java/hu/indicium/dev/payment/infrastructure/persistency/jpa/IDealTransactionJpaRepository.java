package hu.indicium.dev.payment.infrastructure.persistency.jpa;

import hu.indicium.dev.payment.domain.model.transaction.IDealTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IDealTransactionJpaRepository extends JpaRepository<IDealTransaction, UUID> {
    Optional<IDealTransaction> findByExternalId(String externalId);
}
