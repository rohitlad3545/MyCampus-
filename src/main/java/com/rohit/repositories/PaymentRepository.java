package com.rohit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByStudentFeeId(Long studentFeeId);

}
