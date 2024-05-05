package dev.ayush.paymentservice.services;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import dev.ayush.paymentservice.exceptions.PaymentGatewayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentService implements PaymentService {
    private final RazorpayClient razorpayClient;

    public RazorpayPaymentService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String createPaymentLink(String orderId) throws PaymentGatewayException {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", 1000); // 10.01 => 1001
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", true);
        paymentLinkRequest.put("first_min_partial_amount", 100);
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + 15 * 60 * 1000);
        paymentLinkRequest.put("reference_id", orderId);
        paymentLinkRequest.put("description", "Payment for policy no #23456");

        // Order order = orderService.getOrderDetails(orderId);
        // String customerName = order.getUser().getName();
        // String customerContact = order.getUser().getContact();
        // String customerEmail = order.getUser().getEmail();

        JSONObject customer = new JSONObject();
        customer.put("name", "+918824135829");
        customer.put("contact", "Yashasvi Galav");
        customer.put("email", "galavyashasvi23@gmail.com");
        paymentLinkRequest.put("customer", customer);
        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("reminder_enable", true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name", "Jeevan Bima");
        paymentLinkRequest.put("notes", notes);
        paymentLinkRequest.put("callback_url", "https://naman.dev/");
        paymentLinkRequest.put("callback_method", "get");

        PaymentLink paymentLink;
        try {
            paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            return paymentLink.get("short_url").toString();
        } catch (RazorpayException e) {
            throw new PaymentGatewayException(e);
        }
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        /// Go to DB
        //  check if the status of payment in DB
        //  if no:
        //  call the payment gateway to check status of payment
        //  update that status into its own DB
        //  return the status
        return null;
    }

    @Override
    public void handleWebhookEvent(String payload) {

    }
}
