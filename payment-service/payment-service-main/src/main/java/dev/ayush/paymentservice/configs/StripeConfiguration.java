package dev.ayush.paymentservice.configs;

import com.stripe.StripeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {

    @Bean
    public StripeClient stripeClient() {
        return new StripeClient("sk_test_4eC39HqLyjWDarjtT1zdp7dc");
    }
}
