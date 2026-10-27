
package com.rohit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.HOD;

@Repository
public interface HODRepository extends JpaRepository<HOD, Long> {

    Optional<HOD> findByUserId(long id);

    Optional<HOD> findByUserUsername(String username);
}
