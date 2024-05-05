package dev.ayush.paymentservice.services;

import dev.ayush.paymentservice.exceptions.PaymentGatewayException;

public interface PaymentService {
    String createPaymentLink(String orderId) throws PaymentGatewayException;

    String getPaymentStatus(String paymentId);

    void handleWebhookEvent(String payload);
}
