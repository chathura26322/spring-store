package com.codewithmosh.store;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//@Component
public class OrderService {
    private  PaymentService paymentService;

 //autowired use for identify if there is multiple constructors and which one spring should use to run the application
    public OrderService(PaymentService paymentService) {

        this.paymentService = paymentService;
        System.out.println("Order service created");
    }

    @PostConstruct
    public void init(){
        System.out.println("OrderService Post Constructor");
    }

    @PreDestroy
    public void cleanup(){
        System.out.println("OrderService Destroy");
    }

    public void placeOrder(){
        paymentService.processPayment(10);
    }
}
