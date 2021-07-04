package hu.indicium.dev.payment;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.infrastructure.persistency.jpa.PaymentJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
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
                payment.cancel();
            }
        }
    }
}
