package hu.indicium.dev.payment.application.query;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment getPaymentById(PaymentId paymentId) {
        return paymentRepository.getPaymentById(paymentId);
    }

    @Override
    public Collection<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}
