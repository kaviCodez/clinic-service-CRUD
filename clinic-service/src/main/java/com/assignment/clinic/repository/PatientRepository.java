package com.assignment.clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.clinic.entity.Patient;

/**
 * 
 * @author Kavitha Repository Interface for Patient CRUD operations
 *
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	Optional<Patient> findByPatientId(Long id);

	void deleteByPatientId(Long id);

	void save(Optional<Patient> patient);
}
