package com.rohit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

}
