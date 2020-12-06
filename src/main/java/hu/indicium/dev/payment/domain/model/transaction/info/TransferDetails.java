package hu.indicium.dev.payment.domain.model.transaction.info;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class TransferDetails extends BaseDetails {
    @Column(name = "description")
    private String description;

    @Column(name = "transferred_at")
    private Date transferredAt;
}
