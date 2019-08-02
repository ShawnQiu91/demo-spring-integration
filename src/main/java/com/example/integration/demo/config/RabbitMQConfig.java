package com.example.integration.demo.config;

import com.example.integration.demo.service.MsgConsumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EQU_EXCHANGE = "exchange.equ";
    public static final String EQU_QUEUE = "queue.equ";
    @Bean
    public Binding bindAFanoutExchangeToAFanoutQueue(Queue equQueue, FanoutExchange equExchange) {
        return BindingBuilder.bind(equQueue).to(equExchange);
    }
    @Bean
    public FanoutExchange equExchange() {
        return new FanoutExchange(RabbitMQConfig.EQU_EXCHANGE);
    }
    @Bean
    public Queue equQueue() {
        return new Queue(RabbitMQConfig.EQU_QUEUE);
    }

    /**
     * 消息消费者
     * @return
     */
    @Bean
    public MsgConsumer msgConsumer(){
        return new MsgConsumer();
    }
}
