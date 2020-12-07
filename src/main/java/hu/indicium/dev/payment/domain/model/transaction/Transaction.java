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
@NoArgsConstructor
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

    public Transaction(TransactionId transactionId, Double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
    }

    public Double getPaidAmount() {
        if (status.equals(TransactionStatus.PAID)) {
            return amount;
        }
        return 0.0;
    }

    public boolean isPaid() {
        return status.equals(TransactionStatus.PAID);
    }

    public boolean isPending() {
        return status.equals(TransactionStatus.PENDING);
    }

    public boolean isFailed() {
        return status.equals(TransactionStatus.FAILED);
    }

    public boolean isExpired() {
        return status.equals(TransactionStatus.EXPIRED);
    }

    public boolean isCancelled() {
        return status.equals(TransactionStatus.CANCELED);
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
