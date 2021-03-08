package hu.indicium.dev.payment.infrastructure.web.controllers;

import hu.indicium.dev.payment.application.commands.NewPaymentCommand;
import hu.indicium.dev.payment.application.query.PaymentQueryService;
import hu.indicium.dev.payment.application.service.PaymentService;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.infrastructure.util.Response;
import hu.indicium.dev.payment.infrastructure.util.ResponseBuilder;
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

    @GetMapping( "/payments")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<PaymentDto>> getAllPayments() {
        Collection<Payment> payments = paymentQueryService.getAllPayments();
        Collection<PaymentDto> paymentDtos = payments.stream()
                .map(PaymentDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(paymentDtos)
                .build();
    }

    @GetMapping( "/payments/{paymentUuid}")
    @ResponseStatus(HttpStatus.OK)
    public Response<PaymentDto> getPaymentById(@PathVariable UUID paymentUuid) {
        PaymentId paymentId = PaymentId.fromId(paymentUuid);
        Payment payment = paymentQueryService.getPaymentById(paymentId);
        PaymentDto paymentDto = new PaymentDto(payment);
        return ResponseBuilder.ok()
                .data(paymentDto)
                .build();
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PaymentDto> createPayment(@RequestBody NewPaymentCommand newPaymentCommand) {
        PaymentId paymentId = paymentService.createPayment(newPaymentCommand);
        Payment payment = paymentQueryService.getPaymentById(paymentId);
        PaymentDto paymentDto = new PaymentDto(payment);
        return ResponseBuilder.created()
                .data(paymentDto)
                .build();
    }
}
