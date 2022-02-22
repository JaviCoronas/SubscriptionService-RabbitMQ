package com.example.demo.controllers;

import com.example.demo.config.RabbitConfiguration;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PublishController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @Retryable(value = {AmqpException.class}, maxAttemptsExpression = "#{${server.retry.policy.max.attempts:3}}", backoff = @Backoff(delayExpression = "#{${server.retry.policy.delay:36000}}", multiplierExpression = "#{${server.retry.policy.multiplier:2}}", maxDelayExpression = "#{${server.retry.policy.max.delay:252000}}"))
    public void doSendMessageToRabbitMQ(String subscriptionJson) throws AmqpException {

    }

    @RabbitListener(queues = RabbitConfiguration.QUEUE_NAME, containerFactory = "customRabbitListenerContainerFactory")
    @RabbitHandler
    public void onMessageFromRabbitMQ(@Payload String payload, Message message, Channel channel)
            throws InterruptedException {
        try {

            System.out.println(payload);
            //TODO: Save the subscription object here

        } catch (Exception e) {
            System.out.println("Error al guardar o asignar el pedido recibido de Shopify de cola RabbitMQ");
        }
    }
}
