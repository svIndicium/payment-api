package hu.indicium.dev.payment.application.service;

import hu.indicium.dev.payment.application.commands.NewPaymentCommand;
import hu.indicium.dev.payment.domain.model.payment.PaymentId;

public interface PaymentService {
    PaymentId createPayment(NewPaymentCommand newPaymentCommand);
}
