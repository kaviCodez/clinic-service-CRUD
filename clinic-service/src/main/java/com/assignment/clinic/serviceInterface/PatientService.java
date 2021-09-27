package com.assignment.clinic.serviceInterface;

import java.util.List;
import java.util.Optional;

import com.assignment.clinic.entity.Patient;

/**
 * 
 * @author Kavitha Interface for patient service
 *
 */
public interface PatientService {
	Patient savePatient(Patient patient);

	Optional<Patient> getPatientById(Long id);

	void delete(Long id);

	List<Patient> getAllPatients();

	Patient updatePatient(Patient patient);
}
