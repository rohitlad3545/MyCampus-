package com.rohit.entites;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class FeeStructure {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @ManyToOne
	    @JoinColumn(name = "course_id", nullable = false)
	    private Course course;

	    @ManyToOne
	    @JoinColumn(name = "semester_id", nullable = false)
	    private Semester semester;

	    @ManyToOne
	    @JoinColumn(name = "academic_year_id")
	    private AcademicYear academicYear;

	    private double totalAmount;

	

}
