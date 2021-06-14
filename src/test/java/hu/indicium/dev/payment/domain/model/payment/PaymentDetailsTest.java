package hu.indicium.dev.payment.domain.model.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("PaymentDetails")
class PaymentDetailsTest {

    @Test
    @DisplayName("Create PaymentDetails")
    void shouldCreatePaymentDetails() {
        Date testStartDate = new Date();
        String description = "Contributie";
        PaymentDetails paymentDetails = new PaymentDetails(description);

        assertThat(paymentDetails.getDescription()).isEqualTo(description);
        assertThat(paymentDetails.getCreatedAt()).isAfterOrEqualTo(testStartDate);
    }

    @Test
    @DisplayName("Throw exception when creating PaymentDetails with empty description")
    void shouldThrowExceptionWhenCreatingWithEmptyDescription() {
        try {
            new PaymentDetails("");
            fail("Should not create PaymentDetails with empty description");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().toLowerCase()).contains("description");
        }
    }
}