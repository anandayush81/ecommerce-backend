package dev.ayush.paymentservice.services;

import com.stripe.StripeClient;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {
    private final StripeClient stripeClient;

    public StripePaymentService(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @Override
    public String createPaymentLink(String orderId) {
        return null;
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        return null;
    }

    @Override
    public void handleWebhookEvent(String payload) {

    }
}
