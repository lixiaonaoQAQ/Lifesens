package com.Lifesens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Lifesens.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
	Optional<Staff> findByUsername(String username);
}
