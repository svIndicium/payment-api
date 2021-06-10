package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenTransferTransactionDto extends TransactionDto {

    private String description;

    public OpenTransferTransactionDto(Payment payment) {
        super(payment.getOpenTransferTransaction());
        this.description = payment.getPaymentDetails().getDescription();
    }
}
