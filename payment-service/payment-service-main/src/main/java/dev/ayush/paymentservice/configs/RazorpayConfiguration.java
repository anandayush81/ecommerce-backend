package dev.ayush.paymentservice.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfiguration {

    @Bean
    public RazorpayClient razorpayClient(@Value("${razorpay.key_id}") String razorpayKeyId,
                                         @Value("${razorpay.key_secret}") String razorpaySecret) throws RazorpayException {
        return new RazorpayClient(razorpayKeyId, razorpaySecret);
    }
}
