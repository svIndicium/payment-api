package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.AssertionConcern;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Transaction extends AssertionConcern {
    @EmbeddedId
    private TransactionId transactionId;

    private TransactionStatus status;

    @ManyToOne
    private Payment payment;

    private Double amount;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date finishedAt;

    public Double getPaidAmount() {
        if (status.equals(TransactionStatus.PAID)) {
            return amount;
        }
        return 0.0;
    }

    public void setTransactionId(TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }
}
