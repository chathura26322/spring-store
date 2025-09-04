package com.codewithmosh.store.exercise;

import org.springframework.stereotype.Service;

@Service("SMS")
public class SMSNotificationService implements NotificationService{
    @Override
    public void send(String message) {
        System.out.println("SMS Notification Service...");
        System.out.println("Notification: " + message);
    }
}
