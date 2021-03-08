package hu.indicium.dev.payment.infrastructure.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue() {
        return new Queue("payment");
    }

    @Bean
    Exchange exchange() {
        return new DirectExchange("indicium");
    }
}
