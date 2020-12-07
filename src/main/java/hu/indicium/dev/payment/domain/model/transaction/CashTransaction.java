package hu.indicium.dev.payment.domain.model.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
public class CashTransaction extends Transaction {
    public CashTransaction(TransactionId transactionId, Double amount) {
        super(transactionId, amount);
        this.setStatus(TransactionStatus.PAID);
        this.setFinishedAt(new Date());
    }
}
