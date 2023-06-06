package br.com.sysprise.email.infra.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue_venda_material}")
    private String queueVendaMaterial;

    @Value("${spring.rabbitmq.routing_key_venda_material}")
    private String routingKeyVendaMaterial;

    @Value("${spring.rabbitmq.queue_compra_material}")
    private String queueCompraMaterial;

    @Value("${spring.rabbitmq.routing_key_compra_material}")
    private String routingKeyCompraMaterial;

    @Value("${spring.rabbitmq.exchange_name}")
    private String exchangeName;

    @Bean
    Queue queueVendaMaterial() {
        return new Queue(queueVendaMaterial, true);
    }

    @Bean
    Queue queueCompraMaterial() {
        return new Queue(queueCompraMaterial, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Binding bindingQueueEmail() {
        return BindingBuilder.bind(queueVendaMaterial()).to(exchange()).with(routingKeyVendaMaterial);
    }

    @Bean
    Binding bindingQueueCobranca() {
        return BindingBuilder.bind(queueCompraMaterial()).to(exchange()).with(routingKeyCompraMaterial);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
