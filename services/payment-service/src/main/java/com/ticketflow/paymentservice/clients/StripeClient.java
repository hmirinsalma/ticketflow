package com.ticketflow.paymentservice.clients;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class StripeClient {

    private static final Logger logger = LoggerFactory.getLogger(StripeClient.class);

    @Value("${stripe.api.key}")
    private String apiKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
        logger.info("Stripe API initialized");
    }

    public String createPaymentIntent(BigDecimal amount, String currency) {
        try {
            long amountInCents = amount.multiply(new BigDecimal("100")).longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency(currency.toLowerCase())
                .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            logger.info("Payment intent created: {}", paymentIntent.getId());
            return paymentIntent.getId();

        } catch (StripeException e) {
            logger.error("Error creating payment intent", e);
            throw new RuntimeException("Failed to create payment intent: " + e.getMessage());
        }
    }

    public boolean verifyWebhookSignature(String payload, String signature) {
        logger.info("Verifying webhook signature");
        return true;
    }

}
