package hu.indicium.dev.payment.infrastructure.payment.mollie;

import hu.indicium.dev.payment.application.commands.UpdateTransactionCommand;
import hu.indicium.dev.payment.application.service.TransactionService;
import hu.indicium.dev.payment.application.service.TransactionServiceImpl;
import hu.indicium.dev.payment.domain.model.transaction.IDealTransactionRepository;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionRepository;
import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import hu.indicium.dev.payment.infrastructure.payment.PaymentObject;
import hu.indicium.dev.payment.infrastructure.payment.PaymentProvider;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@EnableAsync
public class MollieService {
    private final IDealTransactionRepository transactionRepository;

    private final PaymentProvider paymentProvider;

    private final TransactionServiceImpl transactionService;

    @Async
    public void updateTransaction(String id) {
        PaymentObject paymentObject = paymentProvider.getPaymentByExternalId(id);

        Transaction transaction = transactionRepository.findIDealTransactionByExternalId(id);

        UpdateTransactionCommand updateTransactionCommand = new UpdateTransactionCommand();
        updateTransactionCommand.setTransactionId(transaction.getTransactionId().getId());
        updateTransactionCommand.setPaymentId(transaction.getPayment().getPaymentId().getId());
        updateTransactionCommand.setTransactionStatus(paymentObject.getStatus());
        updateTransactionCommand.setTransferredAt(paymentObject.getPaidAt());

        transactionService.update(updateTransactionCommand);
    }
}
