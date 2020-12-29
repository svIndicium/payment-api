package hu.indicium.dev.payment.domain.model.transaction.info;

import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TransferDetails extends BaseDetails {
    private String description;

    private transient Double paid;

    public TransferDetails(TransactionStatus transactionStatus, Date transferredAt, String description, Double paid) {
        super(transactionStatus, transferredAt);
        this.description = description;
        this.paid = paid;
    }
}
