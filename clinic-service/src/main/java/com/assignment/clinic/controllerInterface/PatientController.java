package com.assignment.clinic.controllerInterface;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.assignment.clinic.entity.Patient;

/**
 * 
 * @author Kavitha Interface for Patient controller
 *
 */

public interface PatientController {

	ResponseEntity<Patient> savePatient(Patient patient);

	ResponseEntity<Patient> updatePatient(Patient patient);

	ResponseEntity<List<Patient>> getAllPatients();

	ResponseEntity<Patient> getPatients(Long patientId);

	ResponseEntity<Void> deletePatient(Long patientId);

}
