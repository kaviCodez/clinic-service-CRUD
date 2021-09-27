package com.assignment.clinic.controllerInterface;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assignment.clinic.entity.Doctor;
import com.assignment.clinic.entity.Patient;

/**
 * 
 * @author Kavitha Interface for doctor controller
 *
 */
public interface DoctorController {

	ResponseEntity<Doctor> saveDoctor(Doctor doctor);

	ResponseEntity<Doctor> updateDoctor(Doctor doctor);

	ResponseEntity<List<Doctor>> getAllDoctors();

	ResponseEntity<Doctor> getDoctors(Long doctorId);

	ResponseEntity<Void> deleteDoctor(Long doctorId);

	List<Doctor> getDoctorsforpatient(Long patientId);

	List<Patient> getAllPatientsforDoctor(Long doctorId);

	ResponseEntity<HttpStatus> deleteAllDoctors();

}
