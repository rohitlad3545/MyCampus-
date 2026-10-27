package com.rohit.entites;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class StudentFee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "fee_structure_id", nullable = false)
    private FeeStructure feeStructure;

    private double totalAmount;

    private double discount;

    private double lateFee;

    private double amountPaid;

    private double balance;

    private String status;

    private LocalDate dueDate;

}
