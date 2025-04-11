package com.Lifesens.service;

import java.util.List;
import java.util.Optional;

import com.Lifesens.entity.Staff;

public interface StaffService {
    Staff save(Staff staff);
    List<Staff> findAll();
    Optional<Staff> findById(Integer id);
    Optional<Staff> findByUsername(String username);
    void deleteById(Integer id);
}
