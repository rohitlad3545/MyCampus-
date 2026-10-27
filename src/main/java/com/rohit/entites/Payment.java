package com.rohit.entites;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Payment {
	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @ManyToOne
	    @JoinColumn(name = "student_fee_id", nullable = false)
	    private StudentFee studentFee;

	    private double amount;

	    private LocalDateTime paymentDate;

	    private String paymentMethod;

	    private String paymentStatus;

	    @Column(unique = true)
	    private String transactionId;

}
