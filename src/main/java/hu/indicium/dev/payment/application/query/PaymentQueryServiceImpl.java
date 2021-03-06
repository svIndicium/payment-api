package hu.indicium.dev.payment.application.query;

import hu.indicium.dev.payment.domain.model.member.MemberId;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;
import hu.indicium.dev.payment.domain.model.payment.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    @Override
    @PostAuthorize("hasPermission('admin:payment') || (hasPermission('read:payment') && userIdEquals(returnObject.memberId.authId))")
    public Payment getPaymentById(PaymentId paymentId) {
        return paymentRepository.getPaymentById(paymentId);
    }

    @Override
    @PreAuthorize("hasPermission('admin:payment')")
    public Collection<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    @Override
    @PreAuthorize("hasPermission('admin:payment') || (hasPermission('read:payment') && userIdEquals(#memberId.authId))")
    public Collection<Payment> getPaymentsByMemberId(MemberId memberId) {
        return paymentRepository.getPaymentsByMemberId(memberId);
    }

    @Override
    @PreAuthorize("hasPermission('admin:payment')")
    public Collection<Payment> getPaymentsWithOpenTransferTransactions() {
        return paymentRepository.getPaymentsWithOpenTransferTransactions();
    }

    @Override
    @PreAuthorize("hasPermission('admin:payment')")
    public Collection<Payment> getPaymentsWithOpenTransferTransactionsByMemberId(MemberId memberId) {
        return paymentRepository.getPaymentsWithOpenTransferTransactionsByMemberId(memberId);
    }
}
