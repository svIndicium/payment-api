package hu.indicium.dev.payment.domain.model.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("PaymentId")
class PaymentIdTest {

    @Test
    @DisplayName("Create PaymentId")
    public void shouldCreatePaymentId() {
        UUID uuid = UUID.randomUUID();
        PaymentId paymentId = PaymentId.fromId(uuid);
        assertThat(paymentId.getId()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("Compare PaymentId")
    public void shouldEqualWhenTwoPaymentIdWithSameUuid() {
        UUID uuid = UUID.randomUUID();
        PaymentId paymentId = PaymentId.fromId(uuid);
        PaymentId paymentId1 = PaymentId.fromId(uuid);
        assertThat(paymentId).isEqualTo(paymentId1);
    }
}