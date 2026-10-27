package com.rohit.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String name;


    @Column(unique = true)
    private String rollNo;


    private String phone;


    @Column(unique = true)
    private String email;


    private String address;


    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
}