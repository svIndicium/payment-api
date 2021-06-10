package hu.indicium.dev.payment.infrastructure.payment.mollie;

import be.woutschoovaerts.mollie.Client;
import be.woutschoovaerts.mollie.data.common.Amount;
import be.woutschoovaerts.mollie.data.payment.PaymentRequest;
import be.woutschoovaerts.mollie.data.payment.PaymentResponse;
import be.woutschoovaerts.mollie.exception.MollieException;
import hu.indicium.dev.payment.infrastructure.payment.PaymentDetails;
import hu.indicium.dev.payment.infrastructure.payment.PaymentObject;
import hu.indicium.dev.payment.infrastructure.payment.PaymentProvider;
import hu.indicium.dev.payment.infrastructure.setting.SettingService;
import hu.indicium.dev.payment.infrastructure.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MollieProvider implements PaymentProvider {

    private final Client client;

    private final SettingService settingService;

    @Override
    public PaymentObject createPayment(PaymentDetails paymentDetails) {
        try {
            Amount amount1 = Amount.builder()
                    .value(Util.formatCurrency(paymentDetails.getAmount()))
                    .currency("EUR")
                    .build();
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("transactionId", paymentDetails.getTransactionId());
            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .amount(amount1)
                    .redirectUrl(Optional.of(paymentDetails.getRedirectUrl()))
                    .description(paymentDetails.getDescription())
                    .webhookUrl(Optional.of(settingService.getValueByKey("MOLLIE_WEBHOOK_URL")))
                    .metadata(metadata)
                    .build();
            PaymentResponse paymentResponse = client.payments().createPayment(paymentRequest);
            return new MolliePayment(paymentResponse);
        } catch (MollieException e) {
            e.printStackTrace();
            throw new IllegalStateException("Mollie:" + e.getMessage());
        }
    }

    @Override
    public PaymentObject getPaymentByExternalId(String externalTransactionId) {
        try {
            PaymentResponse paymentResponse = client.payments().getPayment(externalTransactionId);
            return new MolliePayment(paymentResponse);
        } catch (MollieException e) {
            e.printStackTrace();
            throw new IllegalStateException("Mollie:" + e.getMessage());
        }
    }
}
