package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
public class TransferTransaction extends Transaction implements UpdatableTransaction<TransferDetails> {

    @Embedded
    private TransferDetails transferDetails;

    public TransferTransaction(TransactionId transactionId, Double amount) {
        super(transactionId, amount);
    }

    @Override
    public void updateTransaction(TransferDetails updatedTransactionInfo) {
        if (updatedTransactionInfo.getTransactionStatus().equals(TransactionStatus.PAID)) {
            finishTransaction(updatedTransactionInfo);
        }
    }

    private void finishTransaction(TransferDetails transferDetails) {
        this.setAmount(transferDetails.getPaid());
        this.setStatus(TransactionStatus.PAID);
        this.setFinishedAt(new Date());
        this.setTransferDetails(transferDetails);
    }

    private void setTransferDetails(TransferDetails transferDetails) {
        this.transferDetails = transferDetails;
    }
}
