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

    private Date transferredAt;

    private transient Double paid;

    public TransferDetails(TransactionStatus transactionStatus, String description, Date transferredAt, Double paid) {
        super(transactionStatus);
        this.description = description;
        this.transferredAt = transferredAt;
        this.paid = paid;
    }
}
