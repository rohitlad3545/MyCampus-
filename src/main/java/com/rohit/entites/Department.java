package com.rohit.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Department {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @Column(nullable = false)
	    private String name;

	    private String description;

}
