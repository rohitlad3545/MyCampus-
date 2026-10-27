package com.rohit.entites;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Timetable {
	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @ManyToOne
	    @JoinColumn(name = "course_id")
	    private Course course;

	    @ManyToOne
	    @JoinColumn(name = "semester_id")
	    private Semester semester;

	    @ManyToOne
	    @JoinColumn(name = "subject_id")
	    private Subject subject;

	    @ManyToOne
	    @JoinColumn(name = "faculty_id")
	    private Faculty faculty;

	    private String day;

	    private LocalTime startTime;

	    private LocalTime endTime;

}
