package com.systemlabs.order_service.web.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handlerNewOrder(MyPayload in) {
        System.out.println("New order : '" + in.content() + "'");
    }

    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void handlerDeliveredOrder(MyPayload in) {
        System.out.println("Delivered order :  '" + in.content() + "'");
    }
}
