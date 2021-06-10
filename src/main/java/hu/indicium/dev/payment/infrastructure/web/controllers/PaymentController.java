package hu.indicium.dev.payment.infrastructure.web.controllers;

import hu.indicium.dev.payment.application.commands.NewPaymentCommand;
import hu.indicium.dev.payment.application.query.PaymentQueryService;
import hu.indicium.dev.payment.application.service.PaymentService;
import hu.indicium.dev.payment.domain.model.member.MemberId;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.infrastructure.util.Response;
import hu.indicium.dev.payment.infrastructure.util.ResponseBuilder;
import hu.indicium.dev.payment.infrastructure.web.dto.OpenTransferTransactionDto;
import hu.indicium.dev.payment.infrastructure.web.dto.PaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static hu.indicium.dev.payment.infrastructure.util.BaseUrl.API_V1;

@RestController
@AllArgsConstructor
@RequestMapping(API_V1)
public class PaymentController {

    private final PaymentQueryService paymentQueryService;

    private final PaymentService paymentService;

    @GetMapping("/payments")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<PaymentDto>> getAllPayments() {
        var payments = paymentQueryService.getAllPayments();
        var paymentDtos = payments.stream()
                .map(PaymentDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(paymentDtos)
                .build();
    }

    @GetMapping("/payments/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<PaymentDto>> getPaymentsWithOpenTransferTransaction() {
        var payments = paymentQueryService.getPaymentsWithOpenTransferTransactions();
        var paymentDtos = payments.stream()
                .map(PaymentDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(paymentDtos)
                .build();
    }

    @GetMapping("/payments/{paymentUuid}")
    @ResponseStatus(HttpStatus.OK)
    public Response<PaymentDto> getPaymentById(@PathVariable UUID paymentUuid) {
        var paymentId = PaymentId.fromId(paymentUuid);
        var payment = paymentQueryService.getPaymentById(paymentId);
        var paymentDto = new PaymentDto(payment);
        return ResponseBuilder.ok()
                .data(paymentDto)
                .build();
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PaymentDto> createPayment(@RequestBody NewPaymentCommand newPaymentCommand) {
        var paymentId = paymentService.createPayment(newPaymentCommand);
        var payment = paymentQueryService.getPaymentById(paymentId);
        var paymentDto = new PaymentDto(payment);
        return ResponseBuilder.created()
                .data(paymentDto)
                .build();
    }

    @GetMapping("/members/{memberUuid}/payments")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<PaymentDto>> getPaymentsByMemberId(@PathVariable("memberUuid") String memberUuid) {
        var memberId = MemberId.fromAuthId(memberUuid);
        var payments = paymentQueryService.getPaymentsByMemberId(memberId);
        var paymentDtos = payments.stream()
                .map(PaymentDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(paymentDtos)
                .build();

    }

    @GetMapping("/members/{memberUuid}/payments/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<OpenTransferTransactionDto>> getOpenTransferPaymentsByMemberId(@PathVariable("memberUuid") String memberUuid) {
        var memberId = MemberId.fromAuthId(memberUuid);
        var payments = paymentQueryService.getPaymentsWithOpenTransferTransactionsByMemberId(memberId);
        var paymentDtos = payments.stream()
                .map(OpenTransferTransactionDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(paymentDtos)
                .build();

    }
}
