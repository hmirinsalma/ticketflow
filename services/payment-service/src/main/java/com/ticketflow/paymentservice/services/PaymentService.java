package com.ticketflow.paymentservice.services;

import com.ticketflow.paymentservice.dto.PaymentRequest;
import com.ticketflow.paymentservice.entities.Payment;
import java.util.List;

public interface PaymentService {

    Payment createPayment(PaymentRequest request);

    Payment getPaymentById(Long id);

    Payment processWebhook(String payload, String signature);

    List<Payment> getPaymentsByBookingId(Long bookingId);

}
