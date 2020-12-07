package hu.indicium.dev.payment.infrastructure.web.controllers;

import hu.indicium.dev.payment.application.commands.NewTransactionCommand;
import hu.indicium.dev.payment.application.commands.UpdateTransactionCommand;
import hu.indicium.dev.payment.application.query.TransactionQueryService;
import hu.indicium.dev.payment.application.service.TransactionService;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionId;
import hu.indicium.dev.payment.infrastructure.util.Response;
import hu.indicium.dev.payment.infrastructure.util.ResponseBuilder;
import hu.indicium.dev.payment.infrastructure.web.dto.TransactionDto;
import hu.indicium.dev.payment.infrastructure.web.dto.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static hu.indicium.dev.payment.infrastructure.util.BaseUrl.API_V1;

@AllArgsConstructor
@RestController
@RequestMapping(API_V1)
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionQueryService queryService;

    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<TransactionDto>> getAllTransactions() {
        Collection<Transaction> transactions = queryService.getAllTransactions();
        Collection<TransactionDto> transactionDtos = transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(transactionDtos)
                .build();
    }

    @PostMapping("/payments/{paymentId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TransactionDto> createTransaction(@PathVariable UUID paymentId, @RequestBody NewTransactionCommand newTransactionCommand) {
        newTransactionCommand.setPaymentId(paymentId);
        TransactionId transactionId = transactionService.createTransaction(newTransactionCommand);
        Transaction transaction = queryService.getTransactionById(transactionId);
        TransactionDto transactionDto = TransactionMapper.toDto(transaction);
        return ResponseBuilder.created()
                .data(transactionDto)
                 .build();
    }

    @GetMapping("/payments/{paymentId}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<TransactionDto>> createTransaction(@PathVariable UUID paymentId) {
        PaymentId id = PaymentId.fromId(paymentId);
        Collection<Transaction> transactions = queryService.getAllTransactionsByPaymentId(id);
        Collection<TransactionDto> transactionDtos = transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(transactionDtos)
                .build();
    }

    @PutMapping("/payments/{paymentId}/transactions/{transactionId}/details")
    @ResponseStatus(HttpStatus.OK)
    public Response<TransactionDto> updateTransaction(@PathVariable UUID paymentId, @PathVariable UUID transactionId, @RequestBody UpdateTransactionCommand updateTransactionCommand) {
        updateTransactionCommand.setPaymentId(paymentId);
        updateTransactionCommand.setTransactionId(transactionId);
        TransactionId id = TransactionId.fromId(transactionId);
        transactionService.updateTransaction(updateTransactionCommand);
        Transaction transaction = queryService.getTransactionById(id);
        TransactionDto transactionDto = TransactionMapper.toDto(transaction);
        return ResponseBuilder.created()
                .data(transactionDto)
                .build();
    }
}
