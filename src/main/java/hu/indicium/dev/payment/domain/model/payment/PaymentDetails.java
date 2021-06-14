package hu.indicium.dev.payment.domain.model.payment;

import hu.indicium.dev.payment.domain.AssertionConcern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Getter
@NoArgsConstructor
public class PaymentDetails extends AssertionConcern {
    private String description;

    private Date createdAt;

    public PaymentDetails(String description) {
        this.setCreatedAt(new Date());
        this.setDescription(description);
    }

    private void setCreatedAt(Date date) {
        this.createdAt = date;
    }

    private void setDescription(String description) {
        this.assertArgumentNotEmpty(description, "Description should not be empty");

        this.description = description;
    }
}
