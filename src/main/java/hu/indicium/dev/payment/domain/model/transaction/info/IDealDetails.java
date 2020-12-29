package hu.indicium.dev.payment.domain.model.transaction.info;

import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;

@Data
@NoArgsConstructor
public class IDealDetails extends BaseDetails {

    public IDealDetails(TransactionStatus transactionStatus, Date transferredAt) {
        super(transactionStatus, transferredAt);
    }
}
