package com.systemlabs.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    // @Bean
    // CommandLineRunner sendStartupMessage(RabbitTemplate rabbitTemplate) {
    //     return args -> rabbitTemplate.convertAndSend("orders-exchange", "new-orders", "startup-test");
    // }

}
