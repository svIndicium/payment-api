package hu.indicium.dev.payment.domain.model.payment;

import hu.indicium.dev.payment.domain.AssertionConcern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PaymentDetails extends AssertionConcern {
    private String description;

    private Date createdAt;

    public PaymentDetails(String description) {
        this.setCreatedAt(new Date());
        this.description = description;
    }
}
