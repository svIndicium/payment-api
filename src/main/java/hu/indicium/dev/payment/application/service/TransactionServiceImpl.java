package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.NewTransactionCommand;
import hu.indicium.dev.payment.application.commands.UpdateTransactionCommand;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import hu.indicium.dev.payment.domain.model.transaction.*;
import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import hu.indicium.dev.payment.infrastructure.payment.PaymentDetails;
import hu.indicium.dev.payment.infrastructure.payment.PaymentObject;
import hu.indicium.dev.payment.infrastructure.payment.PaymentProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentProvider paymentProvider;

    @Override
    @PreAuthorize("(hasPermission('create:transaction') && #newTransactionCommand.method != 'cash') || (hasPermission('create:transaction') && #newTransactionCommand.method == 'cash' && hasPermission('admin:payment'))")
    public TransactionId createTransaction(NewTransactionCommand newTransactionCommand) {
        var transactionId = transactionRepository.nextIdentity();

        var paymentId = PaymentId.fromId(newTransactionCommand.getPaymentId());

        var payment = paymentRepository.getPaymentById(paymentId);

        this.assignTransactionToPayment(transactionId, newTransactionCommand, payment);

        return transactionId;
    }

    @PreAuthorize("userIdEquals(payment.memberId.authId) || hasPermission('admin:payment')")
    private void assignTransactionToPayment(TransactionId transactionId, NewTransactionCommand newTransactionCommand, Payment payment) {
        if (!payment.canAcceptTransactionAmount(newTransactionCommand.getAmount())) {
            throw new IllegalArgumentException("Er is te veel betaald.");
        }

        var transaction = toTransaction(transactionId, newTransactionCommand);

        payment.assignTransaction(transaction);

        paymentRepository.save(payment);
    }

    @Override
    @PreAuthorize("hasPermission('admin:payment')")
    public void updateTransaction(UpdateTransactionCommand updateTransactionCommand) {
        this.update(updateTransactionCommand);
    }

    public void update(UpdateTransactionCommand updateTransactionCommand) {
        var paymentId = PaymentId.fromId(updateTransactionCommand.getPaymentId());

        var payment = paymentRepository.getPaymentById(paymentId);

        var transactionId = TransactionId.fromId(updateTransactionCommand.getTransactionId());

        var transaction = transactionRepository.getTransactionById(transactionId);

        var baseDetails = DetailFactory.createDetails(transaction.getType(), updateTransactionCommand);

        payment.updateTransaction(transactionId, baseDetails);

        paymentRepository.save(payment);
    }

    private Transaction toTransaction(TransactionId transactionId, NewTransactionCommand newTransactionCommand) {
        switch (newTransactionCommand.getMethod()) {
            case "cash":
                return new CashTransaction(transactionId, newTransactionCommand.getAmount());
            case "transfer":
                return new TransferTransaction(transactionId, newTransactionCommand.getAmount());
            case "ideal":
                var paymentObject = paymentProvider.createPayment(new PaymentDetails(transactionId, newTransactionCommand.getDescription(), newTransactionCommand.getRedirectUrl(), newTransactionCommand.getAmount()));
                var iDealTransaction = IDealTransaction.builder()
                        .checkoutUrl(paymentObject.getCheckoutUrl())
                        .webhookUrl(paymentObject.getWebhookUrl())
                        .expiresAt(paymentObject.getExpiresAt())
                        .redirectUrl(paymentObject.getRedirectUrl())
                        .externalId(paymentObject.getExternalId())
                        .paymentProvider(paymentObject.getPaymentProvider())
                        .build();
                iDealTransaction.setTransactionId(transactionId);
                iDealTransaction.setAmount(newTransactionCommand.getAmount());
                return iDealTransaction;
        }
        throw new RuntimeException("Transaction method not implemented");
    }
}
