package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PaymentDto {
    private UUID id;

    private double amount;

    private PaymentStatus status;

    private String description;

    private Date createdAt;

    public PaymentDto(Payment payment) {
        this.id = payment.getPaymentId().getId();
        this.amount = payment.getAmount();
        this.status = payment.getPaymentStatus();
        this.description = payment.getPaymentDetails().getDescription();
        this.createdAt = payment.getPaymentDetails().getCreatedAt();
    }
}
