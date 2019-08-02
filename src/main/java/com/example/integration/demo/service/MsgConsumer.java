package com.example.integration.demo.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

/**
 * 消息消费者
 */
public class MsgConsumer {
    @RabbitListener(queues = "queue.equ")
    public void handle(@Payload List payLoad, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel){
        for (Object o : payLoad) {
            System.out.println("--------------------mq-consumer----------------");
            System.out.println(o);
        }
    }
}
