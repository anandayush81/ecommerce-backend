package dev.ayush.paymentservice.controllers;

import com.razorpay.RazorpayException;
import com.razorpay.Webhook;
import dev.ayush.paymentservice.dtos.CreatePaymentLinkRequestDto;
import dev.ayush.paymentservice.exceptions.PaymentGatewayException;
import dev.ayush.paymentservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(@Qualifier("razorpayPaymentService") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public String createPaymentLink(@RequestBody CreatePaymentLinkRequestDto createPaymentLinkRequestDto) throws PaymentGatewayException {
        return paymentService.createPaymentLink(createPaymentLinkRequestDto.getOrderId());
    }

    @PostMapping("/webhook")
    public void handleWebhookEvent(@RequestBody Webhook webhook) {
        System.out.println(webhook.toString());
    }
}
