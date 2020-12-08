package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.*;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import hu.indicium.dev.payment.domain.model.transaction.*;
import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;
import hu.indicium.dev.payment.infrastructure.payment.PaymentDetails;
import hu.indicium.dev.payment.infrastructure.payment.PaymentObject;
import hu.indicium.dev.payment.infrastructure.payment.PaymentProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentProvider paymentProvider;

    @Override
    public TransactionId createTransaction(NewTransactionCommand newTransactionCommand) {
        TransactionId transactionId = transactionRepository.nextIdentity();

        PaymentId paymentId = PaymentId.fromId(newTransactionCommand.getPaymentId());

        Payment payment = paymentRepository.getPaymentById(paymentId);

        if (!payment.canAcceptTransactionAmount(newTransactionCommand.getAmount())) {
            throw new IllegalArgumentException("Er is te veel betaald.");
        }

        Transaction transaction = toTransaction(transactionId, newTransactionCommand);

        payment.assignTransaction(transaction);

        paymentRepository.save(payment);

        return transactionId;
    }

    @Override
    public void updateTransaction(UpdateTransactionCommand updateTransactionCommand) {
        PaymentId paymentId = PaymentId.fromId(updateTransactionCommand.getPaymentId());

        Payment payment = paymentRepository.getPaymentById(paymentId);

        TransactionId transactionId = TransactionId.fromId(updateTransactionCommand.getTransactionId());

        Transaction transaction = transactionRepository.getTransactionById(transactionId);

        BaseDetails baseDetails = DetailFactory.createDetails(transaction.getType(), updateTransactionCommand);

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
                PaymentObject paymentObject = paymentProvider.createPayment(new PaymentDetails(transactionId, newTransactionCommand.getDescription(), newTransactionCommand.getRedirectUrl(), newTransactionCommand.getAmount()));
                IDealTransaction iDealTransaction = IDealTransaction.builder()
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
