package com.assignment.clinic.serviceInterface;

import java.util.List;
import java.util.Optional;

import com.assignment.clinic.entity.Doctor;

/**
 * 
 * @author Kavitha Interface for doctor service
 *
 */

public interface DoctorService {

	Doctor saveDoctor(Doctor doctor);

	List<Doctor> getAllDoctors();

	Optional<Doctor> getDoctorById(Long id);

	void delete(Long id);

	Doctor updateDoctor(Doctor doctor);
}
