package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.NewPaymentCommand;
import hu.indicium.dev.payment.domain.model.member.MemberId;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentDetails;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @PreAuthorize("hasPermission('create:payment')")
    public PaymentId createPayment(NewPaymentCommand newPaymentCommand) {
        PaymentId paymentId = paymentRepository.nextIdentity();

        PaymentDetails paymentDetails = new PaymentDetails(newPaymentCommand.getDescription());

        MemberId memberId = MemberId.fromAuthId(newPaymentCommand.getAuthId());

        Payment payment = new Payment(paymentId, memberId, newPaymentCommand.getAmount(), paymentDetails);

        paymentRepository.save(payment);

        return paymentId;
    }
}
