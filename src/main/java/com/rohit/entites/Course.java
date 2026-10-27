package com.rohit.entites;

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
public class Course {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(unique = true)
	    private String code;

	    private int duration;

	    @ManyToOne
	    @JoinColumn(name = "department_id", nullable = false)
	    private Department department;


}
