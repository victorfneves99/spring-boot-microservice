package com.systemlabs.order_service.web.controllers;

import com.systemlabs.order_service.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQDemoController {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    public RabbitMQDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("/send")
    public void postMethodName(@RequestBody Message message) {
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(), message.routingKey(), message.payload());
    }
}

record Message(String routingKey, MyPayload payload) {}

record MyPayload(String content) {}
