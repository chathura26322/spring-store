package com.codewithmosh.store;

import com.codewithmosh.store.exercise.NotificationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(StoreApplication.class, args);
        var orderService= context.getBean(OrderService.class);
//        var notificationService = context.getBean((NotificationManager.class));
        orderService.placeOrder();
//        notificationService.sendNotification("This is a test notification");
    }

}
