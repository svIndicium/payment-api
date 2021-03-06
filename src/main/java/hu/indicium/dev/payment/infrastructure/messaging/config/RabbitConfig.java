package hu.indicium.dev.payment.infrastructure.messaging.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${hu.indicium.mq.exchange-name}")
    private String topicExchangeName;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
}
