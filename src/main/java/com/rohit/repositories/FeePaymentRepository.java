
package com.rohit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.entites.FeePayment;

public interface FeePaymentRepository
        extends JpaRepository<FeePayment, Long> {

    Optional<FeePayment> findByRazorpayOrderId(
            String razorpayOrderId);

    boolean existsByRazorpayPaymentId(
            String razorpayPaymentId);

    List<FeePayment> findByStudentFeeStudentIdAndStatusOrderByCreatedAtDesc(
            Long studentId, String status);
}
