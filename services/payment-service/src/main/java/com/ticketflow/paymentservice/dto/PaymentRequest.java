package com.ticketflow.paymentservice.dto;

import com.ticketflow.paymentservice.entities.PaymentMethod;
import java.math.BigDecimal;

public class PaymentRequest {

    private Long bookingId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod method;

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }

}
