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
public class Notification {
	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    private String title;

	    @Column(length = 1000)
	    private String message;

	    private String type;

	    private boolean isRead;

	    private LocalDateTime createdAt;

}
