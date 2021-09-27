package com.assignment.clinic.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.clinic.controllerInterface.DoctorController;
import com.assignment.clinic.entity.Doctor;
import com.assignment.clinic.entity.Patient;
import com.assignment.clinic.exceptions.ResourceNotFoundException;
import com.assignment.clinic.service.DoctorServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Kavitha Controller methods for Doctor API's Swagger Api Link to view
 *         all the api : http://localhost:9002/swagger-ui.html
 *
 */

@RestController
@RequestMapping("/doctors")
@Slf4j
public class DoctorControllerImpl implements DoctorController {

	@Autowired
	private DoctorServiceImpl doctorService;

	/**
	 * Creates a doctor with auto generated unique id
	 * 
	 * @param doctor doctor info json
	 * @return json response for saved doctor from db
	 */
	@Override
	@PostMapping("/createdoctor")
	public ResponseEntity<Doctor> saveDoctor(@Valid @RequestBody final Doctor doctor) {
		Doctor savedDoctor = this.doctorService.saveDoctor(doctor);
		log.info("Doctor id: {} created successfully", doctor.getDoctorId());
		return new ResponseEntity<Doctor>(savedDoctor, HttpStatus.CREATED);
	}

	/**
	 * Updates Doctor based on the update info passed by the client
	 * 
	 * @param doctor doctor info json
	 * @return http OK status
	 */
	@Override
	@PutMapping("/updatedoctor")
	public ResponseEntity<Doctor> updateDoctor(@Valid @RequestBody final Doctor doctor) {

		this.doctorService.getDoctorById(doctor.getDoctorId())
				.orElseThrow(() -> new ResourceNotFoundException("Not found Doctor with id = " + doctor.getDoctorId()));
		Doctor updatedDoctor = this.doctorService.updateDoctor(doctor);
		log.info("Doctor id: {} updated successfully", doctor.getDoctorId());
		return new ResponseEntity<Doctor>(updatedDoctor, HttpStatus.OK);
	}

	/**
	 * Gets all Doctors from doctors table
	 * 
	 * @return Get All Doctors present in Doctors table
	 */
	@Override
	@GetMapping("/getalldoctors")
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		List<Doctor> doctors = this.doctorService.getAllDoctors();
		if (doctors.isEmpty()) {
			log.error("doctors List is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		log.info("Doctor List of size : {} returned successfully", doctors.size());
		return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
	}

	/**
	 * Get mapping that retrieves the detail of a specific doctor based on the is
	 * passed
	 * 
	 * @param - doctorid id of the doctor
	 * @return - doctor info of the id passed
	 */
	@Override
	@GetMapping("/getdoctorbyid/{doctorid}")
	public ResponseEntity<Doctor> getDoctors(@Valid @PathVariable("doctorid") Long doctorId) {
		Doctor doc = this.doctorService.getDoctorById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Doctor with id = " + doctorId));
		return new ResponseEntity<Doctor>(doc, HttpStatus.OK);
	}

	/**
	 * Deletes a doctor based on doctor id
	 * 
	 * @param doctorId doctor id
	 */
	@Override
	@DeleteMapping("/deletedoctor/{doctorid}")
	public ResponseEntity<Void> deleteDoctor(@Valid @PathVariable("doctorid") Long doctorId) {
		this.doctorService.getDoctorById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Doctor with id to delete = " + doctorId));
		this.doctorService.delete(doctorId);
		log.info("Doctor id: {} deleted successfully", doctorId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	/**
	 * Deletes all records from doctor table
	 * 
	 * @return HTTP NO CONTENT status
	 */
	@Override
	@DeleteMapping("/deletealldoctors")
	public ResponseEntity<HttpStatus> deleteAllDoctors() {
		this.doctorService.deleteAllDoctors();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Get Doctor list based on patient's diagnosis type
	 * 
	 * @param - patient id for which the doctor specialist should be returned
	 * @return - Doctors List based on the patients diagnosis type
	 * 
	 */
	@Override
	@GetMapping("/getdoctorlist/{patientId}")
	public List<Doctor> getDoctorsforpatient(@Valid @PathVariable("patientId") Long patientId) {
		return this.doctorService.getDoctorsforpatient(patientId);
	}

	/**
	 * Find all patients mapped to the doctor
	 * 
	 * @param- doctorId pass id of the doctor whose patient list should be displayed
	 * @return - Get all patients mapped to the doctor
	 * 
	 */
	@Override
	@GetMapping("/getallpatients/{doctorId}")
	public List<Patient> getAllPatientsforDoctor(@Valid @PathVariable("doctorId") Long doctorId) {
		return this.doctorService.getAllPatientsforDoctor(doctorId);
	}

}
