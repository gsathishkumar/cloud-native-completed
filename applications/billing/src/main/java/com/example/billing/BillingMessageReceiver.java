package com.example.billing;

import com.example.payments.Gateway;
import org.springframework.messaging.handler.annotation.Payload;

public class BillingMessageReceiver {
    private final Gateway paymentGateway;

    public BillingMessageReceiver(Gateway paymentGateway) {

        this.paymentGateway = paymentGateway;
    }

    // There is a gotcha here about message formats. There might be messages
    // in the queue when this service is redeployed so if this interface changes
    // the messages in the queue may not be able to be deserialized.
    // Consider a serialization format that is sensitive to iterating on
    // versions like protocol buffers.
    public void process(@Payload BillingMessage message) {
        System.out.println("Received <" + message.getUserId()+ ">");
        int amount = message.getAmount();

        // you may want to move this conditional logic into another method/function since it is now
        // duplicated between the controller and this class. unfortunately there really isn't
        // enough code to know for sure right now.
        if (paymentGateway.createReocurringPayment(amount)){
            System.out.println("Successfully created payment for <" + amount + ">");
        } else {
            System.out.println("Creating payment for <" + amount + "> failed.");
        }
    }
}