package hu.indicium.dev.payment.domain.model.payment;

import hu.indicium.dev.payment.domain.DomainEvent;
import hu.indicium.dev.payment.domain.model.member.MemberId;
import hu.indicium.dev.payment.domain.model.transaction.*;
import hu.indicium.dev.payment.domain.model.transaction.info.IDealDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;
import hu.indicium.dev.payment.util.TestDomainEventSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Payment")
class PaymentTest {

    private PaymentId paymentId;

    private PaymentDetails paymentDetails;

    private MemberId memberId;

    private TestDomainEventSubscriber domainEventSubscriber = TestDomainEventSubscriber.subscribe();

    @BeforeEach
    void setUp() {
        this.paymentId = PaymentId.fromId(UUID.randomUUID());
        this.paymentDetails = new PaymentDetails("Contributie");
        this.memberId = MemberId.fromAuthId(UUID.randomUUID().toString());
        domainEventSubscriber.clear();
    }

    @Test
    @DisplayName("Create payment")
    void shouldCreatePayment() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        assertThat(payment.getPaymentId()).isEqualTo(paymentId);
        assertThat(payment.getMemberId()).isEqualTo(memberId);
        assertThat(payment.getAmount()).isEqualTo(amount);
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(amount);
        assertThat(payment.getRemainingAmount()).isEqualTo(amount);
        assertThat(payment.getTransactions().size()).isZero();
        assertThat(payment.canAcceptTransactionAmount(amount)).isTrue();
        assertThat(payment.canAcceptTransactionAmount(amount + 1)).isFalse();
    }

    @Test
    @DisplayName("Create payment without paymentId")
    void shouldThrowExceptionWhenCreatePaymentWithoutPaymentId() {
        try {
            Double amount = 15.0;
            new Payment(null, memberId, amount, paymentDetails);
            fail("Payment shouldn't be created without paymentId");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().toLowerCase()).contains("order id");
        }
    }

    @Test
    @DisplayName("Create payment without memberId")
    void shouldThrowExceptionWhenCreatePaymentWithoutMemberId() {
        try {
            Double amount = 15.0;
            new Payment(paymentId, null, amount, paymentDetails);
            fail("Payment shouldn't be created without memberId");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().toLowerCase()).contains("gebruikersid");
        }
    }

    @Test
    @DisplayName("Create payment without payment details")
    void shouldThrowExceptionWhenCreatePaymentWithoutPaymentDetails() {
        try {
            Double amount = 15.0;
            new Payment(paymentId, memberId, amount, null);
            fail("Payment shouldn't be created without memberId");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().toLowerCase()).contains("betalingsgegevens");
        }
    }

    @Test
    @DisplayName("Create payment with negative amount")
    void shouldThrowExceptionWhenCreatePaymentWithNegativeAmount() {
        try {
            Double amount = -15.0;
            new Payment(paymentId, memberId, amount, paymentDetails);
            fail("Payment shouldn't be created with negative amount");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().toLowerCase()).contains("negatief");
        }
    }

    @Test
    @DisplayName("Assign cash transaction to payment")
    void shouldAddCashTransactionToPayment() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        assertThat(payment.getAmount()).isEqualTo(amount);
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(amount);
        assertThat(payment.getRemainingAmount()).isEqualTo(amount);
        assertThat(payment.getTransactions().size()).isZero();

        TransactionId transactionId = TransactionId.fromId(UUID.randomUUID());

        CashTransaction cashTransaction = new CashTransaction(transactionId, 10.0);

        payment.assignTransaction(cashTransaction);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(5.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(5.0);
        assertThat(payment.getTransactions().size()).isEqualTo(1);

        TransactionId transactionId1 = TransactionId.fromId(UUID.randomUUID());

        CashTransaction cashTransaction1 = new CashTransaction(transactionId1, 5.0);

        payment.assignTransaction(cashTransaction1);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(payment.getOpenAmount()).isEqualTo(0.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(0.0);
        assertThat(payment.getTransactions().size()).isEqualTo(2);

        List<DomainEvent> occurredEvents = domainEventSubscriber.getEvents();

        assertThat(occurredEvents.size()).isPositive();

        DomainEvent domainEvent = occurredEvents.get(0);
        PaymentUpdated paymentUpdated = (PaymentUpdated) domainEvent;

        assertThat(paymentUpdated.getPayment()).isEqualTo(payment);
    }

    @Test
    @DisplayName("Assign transfer transaction to payment")
    void shouldAddTransferTransactionToPayment() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        assertThat(payment.getAmount()).isEqualTo(amount);
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(amount);
        assertThat(payment.getRemainingAmount()).isEqualTo(amount);
        assertThat(payment.getTransactions().size()).isZero();

        TransactionId transactionId = TransactionId.fromId(UUID.randomUUID());

        TransferTransaction transferTransaction = new TransferTransaction(transactionId, 15.0);

        payment.assignTransaction(transferTransaction);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(0.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(15.0);
        assertThat(payment.getTransactions().size()).isEqualTo(1);

        TransferDetails transferDetails = new TransferDetails(TransactionStatus.PAID, new Date(), "Betaling", 15.0);

        payment.updateTransaction(transactionId, transferDetails);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(payment.getOpenAmount()).isEqualTo(0.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(0.0);
        assertThat(payment.getTransactions().size()).isEqualTo(1);

        List<DomainEvent> occurredEvents = domainEventSubscriber.getEvents();

        assertThat(occurredEvents.size()).isPositive();

        DomainEvent domainEvent = occurredEvents.get(0);
        PaymentUpdated paymentUpdated = (PaymentUpdated) domainEvent;

        assertThat(paymentUpdated.getPayment()).isEqualTo(payment);
    }

    @Test
    @DisplayName("Return open transfer transaction")
    void shouldReturnOpenTransferTransaction() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        TransactionId transactionId = TransactionId.fromId(UUID.randomUUID());

        TransferTransaction transferTransaction = new TransferTransaction(transactionId, 10.0);

        payment.assignTransaction(transferTransaction);

        TransferDetails transferDetails = new TransferDetails(TransactionStatus.PAID, new Date(), "Betaling", 10.0);

        payment.updateTransaction(transactionId, transferDetails);

        TransactionId transactionId1 = TransactionId.fromId(UUID.randomUUID());

        TransferTransaction transferTransaction1 = new TransferTransaction(transactionId1, 5.0);

        payment.assignTransaction(transferTransaction1);

        assertThat(payment.getOpenTransferTransaction()).isEqualTo(transferTransaction1);

        TransferDetails transferDetails1 = new TransferDetails(TransactionStatus.PAID, new Date(), "Betaling", 5.0);

        payment.updateTransaction(transactionId1, transferDetails1);

        assertThat(payment.getOpenTransferTransaction()).isNull();

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(payment.getOpenAmount()).isEqualTo(0.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(0.0);
        assertThat(payment.getTransactions().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Update transaction")
    void shouldThrowExceptionWhenUpdateTransactionUnknownToPayment() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        TransactionId transactionId = TransactionId.fromId(UUID.randomUUID());

        TransferTransaction transferTransaction = new TransferTransaction(transactionId, 15.0);

        payment.assignTransaction(transferTransaction);

        TransferDetails transferDetails = new TransferDetails(TransactionStatus.PAID, new Date(), "Betaling", 15.0);

        TransactionId transactionId1 = TransactionId.fromId(UUID.randomUUID());

        try {
            payment.updateTransaction(transactionId1, transferDetails);
            fail("Transaction is unknown to payment, should throw exception");
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage().toLowerCase()).contains("transaction");
        }
    }

    @Test
    @DisplayName("Process 3 different transaction types")
    void shouldProcessThreeTransactions() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        TransactionId transactionId = TransactionId.fromId(UUID.randomUUID());

        TransferTransaction transferTransaction = new TransferTransaction(transactionId, 5.0);

        payment.assignTransaction(transferTransaction);
        TransferDetails transferDetails = new TransferDetails(TransactionStatus.PAID, new Date(), "Betaling", 5.0);

        payment.updateTransaction(transactionId, transferDetails);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(10.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(10.0);
        assertThat(payment.getTransactions().size()).isEqualTo(1);

        TransactionId transactionId1 = TransactionId.fromId(UUID.randomUUID());

        CashTransaction cashTransaction = new CashTransaction(transactionId1, 5.0);

        payment.assignTransaction(cashTransaction);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.OPEN);
        assertThat(payment.getOpenAmount()).isEqualTo(5.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(5.0);
        assertThat(payment.getTransactions().size()).isEqualTo(2);

        TransactionId transactionId2 = TransactionId.fromId(UUID.randomUUID());

        IDealTransaction iDealTransaction = new IDealTransaction(transactionId2, 5.0);

        payment.assignTransaction(iDealTransaction);

        IDealDetails iDealDetails = new IDealDetails(TransactionStatus.PAID, new Date());

        payment.updateTransaction(transactionId2, iDealDetails);

        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(payment.getOpenAmount()).isEqualTo(0.0);
        assertThat(payment.getRemainingAmount()).isEqualTo(0.0);
        assertThat(payment.getTransactions().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Paying more should throw exception")
    void shouldThrowExceptionWhenPaidMore() {
        Double amount = 15.0;
        Payment payment = new Payment(paymentId, memberId, amount, paymentDetails);

        TransactionId transactionId = TransactionId.fromId(UUID.randomUUID());

        CashTransaction cashTransaction = new CashTransaction(transactionId, 20.0);

        try {
            payment.assignTransaction(cashTransaction);
            fail("Should throw exception because paid too much!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().toLowerCase()).contains("betaald");
        }
    }

    @Test
    @DisplayName("No args constructor")
    void shouldCreatePaymentWithNoArgsConstructor() {
        Payment payment = new Payment();
        assertThat(payment.getPaymentId()).isNull();
    }
}