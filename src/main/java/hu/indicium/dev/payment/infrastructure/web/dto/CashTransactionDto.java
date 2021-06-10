package hu.indicium.dev.payment.infrastructure.web.dto;


import hu.indicium.dev.payment.domain.model.transaction.CashTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashTransactionDto extends TransactionDto {
    public CashTransactionDto(CashTransaction cashTransaction) {
        super(cashTransaction);
    }
}
