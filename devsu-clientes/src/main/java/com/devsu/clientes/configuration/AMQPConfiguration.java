package com.devsu.clientes.configuration;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Configuration
public class AMQPConfiguration {

    @Bean
    public TopicExchange movimientosTopicExchange(@Value("${amqp.exchange.movimientos}") String exchange) {
        return ExchangeBuilder.topicExchange(exchange).durable(true).build();
    }

    @Bean
    public Queue movimientosQueue(@Value("${amqp.queue.movimientos}") String queue) {
        return QueueBuilder.durable(queue).build();
    }

    @Bean
    public Binding movimientosBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("movimiento");
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        final DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        final MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();

        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(PROPERTIES));
        factory.setMessageConverter(jsonConverter);

        return factory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }
}
