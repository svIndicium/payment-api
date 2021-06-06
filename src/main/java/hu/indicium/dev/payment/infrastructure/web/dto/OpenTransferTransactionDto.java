package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.transaction.TransferTransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OpenTransferTransactionDto extends TransactionDto {

    private String description;

    public OpenTransferTransactionDto(Payment payment) {
        super(payment.getOpenTransferTransaction());
        this.description = payment.getPaymentDetails().getDescription();
    }
}
