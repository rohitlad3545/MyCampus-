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
public class Result {
	   @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @ManyToOne
	    @JoinColumn(name = "student_id", nullable = false)
	    private Student student;

	    @ManyToOne
	    @JoinColumn(name = "subject_id", nullable = false)
	    private Subject subject;

	    @ManyToOne
	    @JoinColumn(name = "exam_id", nullable = false)
	    private Exam exam;

	    private Double marks;

	    private String grade;

	    private Double gradePoint;

}
