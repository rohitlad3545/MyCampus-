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
public class Resource {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    private String title;

	    @Column(length = 1000)
	    private String description;

	    private String fileUrl;

	    @ManyToOne
	    @JoinColumn(name = "subject_id")
	    private Subject subject;

	    @ManyToOne
	    @JoinColumn(name = "faculty_id")
	    private Faculty faculty;

	    private LocalDateTime uploadedAt;

}
