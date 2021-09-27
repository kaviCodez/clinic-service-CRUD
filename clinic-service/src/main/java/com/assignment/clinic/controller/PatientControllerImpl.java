package com.assignment.clinic.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.clinic.controllerInterface.PatientController;
import com.assignment.clinic.entity.Patient;
import com.assignment.clinic.exceptions.ResourceNotFoundException;
import com.assignment.clinic.service.PatientServiceImpl;

/**
 * 
 * @author Kavitha Controller methods for Patient API's 
 * Swagger Api Link to view all the api : http://localhost:9002/swagger-ui.html
 *
 */

@RestController
@RequestMapping("/patients")
@Slf4j
public class PatientControllerImpl implements PatientController {

	@Autowired
	private PatientServiceImpl patientService;

	/**
	 * Creates a patient with auto generated unique id
	 * 
	 * @param patient patient info json
	 * @return json response for saved patient from db
	 */
	@Override
	@PostMapping("/createpatient")
	public ResponseEntity<Patient> savePatient(@Valid @RequestBody final Patient patient) {
		Patient savedPatient = this.patientService.savePatient(patient);
		log.info("Patient id: {} created successfully", patient.getPatientId());
		return new ResponseEntity<Patient>(savedPatient, HttpStatus.CREATED);
	}

	/**
	 * Updates Patient based on the update info passed by the client
	 * 
	 * @param patient patient info json
	 * @return http OK status
	 */
	@Override
	@PutMapping("/updatepatient")
	public ResponseEntity<Patient> updatePatient(@Valid @RequestBody final Patient patient) {
		
		this.patientService.getPatientById(patient.getPatientId())
				.orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id = " + patient.getPatientId()));
		Patient updatedPatient = this.patientService.updatePatient(patient);
		log.info("Patient id: {} updated successfully", patient.getPatientId());
		return new ResponseEntity<Patient>(updatedPatient,HttpStatus.OK);
	}

	/**
	 * Gets all Patients from patients table
	 * 
	 * @return Get All patients present in Patients table
	 */
	@Override
	@GetMapping("/getallpatients")
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = this.patientService.getAllPatients();
		if (patients.isEmpty()) {
			log.error("Patients List is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		log.info("Patient List of size : {} returned successfully", patients.size());
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	/**
	 * Get mapping that retrieves the detail of a specific patient based on the is
	 * passed
	 * 
	 * @param - patient id of the doctor
	 * @return - patient info of the id passed
	 */
	@Override
	@GetMapping("/getpatientbyid/{patientid}")
	public ResponseEntity<Patient> getPatients(@Valid @PathVariable("patientid") Long patientId) {
		Patient patient = this.patientService.getPatientById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id = " + patientId));
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	/**
	 * Deletes a patient based on patient id
	 * 
	 * @param patientId patient id
	 */
	@Override
	@DeleteMapping("/deletepatient/{patientid}")
	public ResponseEntity<Void> deletePatient(@Valid @PathVariable("patientid") Long patientId) {
		this.patientService.getPatientById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id = " + patientId));
		this.patientService.delete(patientId);
		log.info("Patient id: {} deleted successfully", patientId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

}
