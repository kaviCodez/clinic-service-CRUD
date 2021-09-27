package com.assignment.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.clinic.entity.DoctorSpecialization;

/**
 * 
 * @author Kavitha Repository Interface for DiagnosisType CRUD operations
 *
 */
@Repository
public interface DoctorDiagnosisRepository extends JpaRepository<DoctorSpecialization, Long> {

}
