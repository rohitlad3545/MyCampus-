
package com.rohit.entites;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "fee_payments",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_fee_payment_order",
            columnNames = "razorpay_order_id"
        ),
        @UniqueConstraint(
            name = "uk_fee_payment_payment",
            columnNames = "razorpay_payment_id"
        )
    }
)
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_fee_id", nullable = false)
    private StudentFee studentFee;

    @Column(name = "razorpay_order_id",
            nullable = false, unique = true)
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id", unique = true)
    private String razorpayPaymentId;

    private Long amountPaise;

    private String currency = "INR";

    // CREATED, PAID, FAILED, EXPIRED
    private String status = "CREATED";

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
