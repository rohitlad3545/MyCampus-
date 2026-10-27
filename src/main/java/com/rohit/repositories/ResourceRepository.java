package com.rohit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>{

}
