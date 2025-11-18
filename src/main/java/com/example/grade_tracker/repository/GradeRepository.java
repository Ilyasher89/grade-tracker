package com.example.grade_tracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grade_tracker.model.Grade;
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
