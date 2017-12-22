package com.example.billing;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitClient implements Client {
    private final String queueName;
    private final RabbitTemplate rabbitTemplate;

    public RabbitClient(String queueName, RabbitTemplate rabbitTemplate) {
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void billUser(String userId, int amount) {
        rabbitTemplate.convertAndSend(queueName, new BillingMessage(userId, amount));
    }
}