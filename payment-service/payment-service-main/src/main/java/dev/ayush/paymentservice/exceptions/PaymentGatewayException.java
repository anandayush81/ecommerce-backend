package dev.ayush.paymentservice.exceptions;

public class PaymentGatewayException extends Exception {
    public PaymentGatewayException(Exception e) {
        super(e);
    }
}
