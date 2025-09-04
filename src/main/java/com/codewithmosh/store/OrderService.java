package com.codewithmosh.store;


//@Component
public class OrderService {
    private  PaymentService paymentService;

 //autowired use for identify if there is multiple constructors and which one spring should use to run the application
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder(){
        paymentService.processPayment(10);
    }
}
