package hu.indicium.dev.payment.domain.model.payment;

import hu.indicium.dev.payment.domain.AssertionConcern;
import hu.indicium.dev.payment.domain.DomainEventPublisher;
import hu.indicium.dev.payment.domain.model.member.MemberId;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Payment extends AssertionConcern {
    @EmbeddedId
    private PaymentId paymentId;

    private Double amount;

    @Embedded
    private MemberId memberId;

    @Embedded
    private PaymentDetails paymentDetails;

    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Payment(PaymentId paymentId, MemberId memberId, Double amount, PaymentDetails paymentDetails) {
        this.setPaymentId(paymentId);
        this.setAmount(amount);
        this.setMemberId(memberId);
        this.setPaymentStatus(PaymentStatus.OPEN);
        this.setPaymentDetails(paymentDetails);
    }

    public boolean canAcceptTransactionAmount(Double amount) {
        return getOpenAmount() >= amount;
    }

    public Double getRemainingAmount() {
        Double alreadyPaid = 0.0;
        for (Transaction transaction : transactions) {
            alreadyPaid += transaction.getPaidAmount();
        }
        return amount - alreadyPaid;
    }

    public Double getOpenAmount() {
        Double open = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.isPending() || transaction.isPaid()) {
                open += transaction.getAmount();
            }
        }
        return amount - open;
    }

    private void setPaymentId(PaymentId orderId) {
        assertArgumentNotNull(orderId, "Order ID moet worden meegegeven.");

        this.paymentId = orderId;
    }

    private void setAmount(Double amount) {
        assertArgumentTrue(amount > 0.0, "Bedrag mag niet negatief zijn.");

        this.amount = amount;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        assertArgumentNotNull(paymentStatus, "Betalingstatus moet worden meegegeven.");

        this.paymentStatus = paymentStatus;
        DomainEventPublisher.instance()
                .publish(new PaymentUpdated(this));
    }

    private void setPaymentDetails(PaymentDetails paymentDetails) {
        assertArgumentNotNull(paymentDetails, "Betalingsgegevens moet worden meegegeven.");

        this.paymentDetails = paymentDetails;
    }

    private void setMemberId(MemberId memberId) {
        assertArgumentNotNull(memberId, "GebruikersID moet worden meegegeven.");

        this.memberId = memberId;
    }

    public void assignTransaction(Transaction transaction) {
        assertArgumentTrue(paymentStatus.equals(PaymentStatus.OPEN), "Betaling is al betaald of geannuleerd.");
        assertArgumentTrue(getOpenAmount() >= transaction.getAmount(), "Er is te veel betaald.");

        transaction.setPayment(this);
        this.transactions.add(transaction);
        this.updateStatus();
    }

    private Transaction getTransactionById(TransactionId transactionId) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Transaction %s not found in payment %s", transactionId.getId().toString(), this.getPaymentId().getId().toString())));
    }

    public void updateTransaction(TransactionId transactionId, BaseDetails baseDetails) {
        Transaction transaction = getTransactionById(transactionId);
        transaction.updateTransaction(baseDetails);
        this.updateStatus();
    }

    private void updateStatus() {
        if (this.getRemainingAmount() == 0.0) {
            this.setPaymentStatus(PaymentStatus.PAID);
        }
    }

}
