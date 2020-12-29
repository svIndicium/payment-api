package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.IDealDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;
import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IDealTransaction extends Transaction {

    private String externalId;

    private String paymentProvider;

    private Date expiresAt;

    private String checkoutUrl;

    private String webhookUrl;

    private String redirectUrl;

    public IDealTransaction(TransactionId transactionId, Double amount) {
        super(transactionId, amount);
    }

    @Builder(toBuilder = true)
    public IDealTransaction(TransactionId transactionId, Double amount, String externalId, String paymentProvider, Date expiresAt, String checkoutUrl, String webhookUrl, String redirectUrl) {
        super(transactionId, amount);
        this.externalId = externalId;
        this.paymentProvider = paymentProvider;
        this.expiresAt = expiresAt;
        this.checkoutUrl = checkoutUrl;
        this.webhookUrl = webhookUrl;
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void updateTransaction(BaseDetails updatedTransactionInfo) {
        if (updatedTransactionInfo instanceof IDealDetails) {
            IDealDetails iDealDetails = (IDealDetails) updatedTransactionInfo;
            this.setStatus(iDealDetails.getTransactionStatus());
            this.setFinishedAt(iDealDetails.getTransferredAt());
        }
    }

    @Override
    public TransactionType getType() {
        return TransactionType.IDEAL;
    }
}
