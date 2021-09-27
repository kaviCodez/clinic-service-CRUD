package com.assignment.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.assignment.clinic.entity.PatientDiagnosis;

/**
 * 
 * @author Kavitha Repository Interface for PatientDiagnosis CRUD operations
 *
 */

public interface PatientDiagnosisRepository extends JpaRepository<PatientDiagnosis, Long> {

}
