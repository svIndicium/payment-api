package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.*;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import hu.indicium.dev.payment.domain.model.transaction.*;
import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import hu.indicium.dev.payment.domain.model.transaction.info.TransferDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PaymentRepository paymentRepository;

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

        BaseDetails baseDetails = toDetails(updateTransactionCommand);

        payment.updateTransaction(transactionId, baseDetails);

        paymentRepository.save(payment);
    }

    private Transaction toTransaction(TransactionId transactionId, NewTransactionCommand newTransactionCommand) {
        if (newTransactionCommand instanceof NewCashTransactionCommand) {
            return new CashTransaction(transactionId, newTransactionCommand.getAmount());
        } else if (newTransactionCommand instanceof NewTransferTransactionCommand) {
            return new TransferTransaction(transactionId, newTransactionCommand.getAmount());
        }
        throw new RuntimeException("Transaction method not implemented");
    }

    private BaseDetails toDetails(UpdateTransactionCommand updateTransactionCommand) {
        if (updateTransactionCommand instanceof UpdateTransferTransactionCommand) {
            UpdateTransferTransactionCommand updateTransferTransactionCommand = (UpdateTransferTransactionCommand) updateTransactionCommand;
            return new TransferDetails(updateTransferTransactionCommand.getTransactionStatus(), updateTransferTransactionCommand.getDescription(), updateTransferTransactionCommand.getTransferredAt(), updateTransferTransactionCommand.getPaid());
        }
        throw new RuntimeException("Transaction method not implemented");
    }
}
