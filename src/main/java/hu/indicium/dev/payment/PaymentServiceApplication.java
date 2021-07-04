package hu.indicium.dev.payment;

import hu.indicium.dev.payment.domain.DomainEventPublisher;
import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.domain.model.payment.PaymentUpdated;
import hu.indicium.dev.payment.infrastructure.persistency.jpa.PaymentJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Slf4j
@Order(10)
public class PaymentServiceApplication implements CommandLineRunner {

    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (Payment payment : paymentJpaRepository.getPaymentsByPaymentDetailsDescription("Contributie 2020-2021")) {
            if (payment.getRemainingAmount() == 15.0) {
                log.info("Checking payment " + payment.getPaymentId().getId().toString() + ". Remaining:" + payment.getRemainingAmount());
                payment.cancel();
                paymentJpaRepository.save(payment);
                DomainEventPublisher.instance().publish(new PaymentUpdated(payment));
            }
        }
    }
}
