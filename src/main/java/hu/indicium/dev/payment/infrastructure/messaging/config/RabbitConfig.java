package hu.indicium.dev.payment.infrastructure.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${RABBITMQ_TOPIC_EXCHANGE_NAME}")
    private String topicExchangeName;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
}
