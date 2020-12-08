package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class TransferTransaction extends Transaction {
    @Column(name = "description")
    private String description;

    @Column(name = "transferred_at")
    private Date transferredAt;

    public TransferTransaction(TransactionId transactionId, Double amount) {
        super(transactionId, amount);
    }

    @Override
    public void updateTransaction(BaseDetails updatedTransactionInfo) {
        if (updatedTransactionInfo instanceof TransferDetails) {
            TransferDetails transferDetails = (TransferDetails) updatedTransactionInfo;
            if (transferDetails.getTransactionStatus().equals(TransactionStatus.PAID)) {
                finishTransaction(transferDetails);
            }
        }
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }

    private void finishTransaction(TransferDetails transferDetails) {
        this.setAmount(transferDetails.getPaid());
        this.setStatus(TransactionStatus.PAID);
        this.setFinishedAt(new Date());
    }
}
