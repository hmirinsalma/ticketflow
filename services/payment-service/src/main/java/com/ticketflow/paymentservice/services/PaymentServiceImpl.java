package com.ticketflow.paymentservice.services;

import com.ticketflow.paymentservice.clients.StripeClient;
import com.ticketflow.paymentservice.dto.PaymentRequest;
import com.ticketflow.paymentservice.entities.Payment;
import com.ticketflow.paymentservice.entities.PaymentStatus;
import com.ticketflow.paymentservice.entities.Transaction;
import com.ticketflow.paymentservice.exceptions.PaymentNotFoundException;
import com.ticketflow.paymentservice.repositories.PaymentRepository;
import com.ticketflow.paymentservice.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StripeClient stripeClient;

    @Override
    @Transactional
    public Payment createPayment(PaymentRequest request) {
        logger.info("Creating payment for booking: {}", request.getBookingId());

        String paymentIntentId = stripeClient.createPaymentIntent(
            request.getAmount(),
            request.getCurrency()
        );

        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId(paymentIntentId);

        payment = paymentRepository.save(payment);
        createTransaction(payment.getId(), "CREATED", "Payment created");

        logger.info("Payment created with ID: {}", payment.getId());
        return payment;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
    }

    @Override
    @Transactional
    public Payment processWebhook(String payload, String signature) {
        logger.info("Processing webhook");

        boolean isValid = stripeClient.verifyWebhookSignature(payload, signature);
        if (!isValid) {
            throw new RuntimeException("Invalid webhook signature");
        }

        logger.info("Webhook processed successfully");
        return null;
    }

    @Override
    public List<Payment> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    private void createTransaction(Long paymentId, String type, String details) {
        Transaction transaction = new Transaction();
        transaction.setPaymentId(paymentId);
        transaction.setType(type);
        transaction.setDetails(details);
        transactionRepository.save(transaction);
    }

}
