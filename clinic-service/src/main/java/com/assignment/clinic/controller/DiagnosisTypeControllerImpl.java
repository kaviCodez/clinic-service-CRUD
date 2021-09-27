package com.assignment.clinic.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.clinic.controllerInterface.DiagnosisTypeController;
import com.assignment.clinic.entity.DiagnosisType;
import com.assignment.clinic.exceptions.ResourceNotFoundException;
import com.assignment.clinic.service.DiagnosisServiceTypeImpl;

/**
 * 
 * @author Kavitha Controller methods for diagnosis type Swagger Api Link to
 *         view all the api : http://localhost:9002/swagger-ui.html
 *
 */

// 
@RestController
@RequestMapping("/diagnosistype")
@Slf4j
public class DiagnosisTypeControllerImpl implements DiagnosisTypeController {

	@Autowired
	private DiagnosisServiceTypeImpl diagnosisService;

	/**
	 * Creates a diagnosis with auto generated unique id
	 * 
	 * @param diagnosis diagnosis info json
	 * @return json response for saved diagnosis from db
	 */
	@Override
	@PostMapping("/creatediagnosistype")
	public ResponseEntity<DiagnosisType> saveDiagnosisType(@Valid @RequestBody final DiagnosisType diagnosisType) {
		DiagnosisType diagType = this.diagnosisService.saveDiagnosisType(diagnosisType);
		log.info("Diagnosis id: {} created successfully", diagType.getTypeId());
		return new ResponseEntity<DiagnosisType>(diagType, HttpStatus.CREATED);
	}

	/**
	 * Updates Diagnosis based on diagnosis json passed
	 * 
	 * @param diagnosis diagnosis info json
	 * @return http OK status
	 */
	@Override
	@PutMapping("/updatediagnosistype")
	public ResponseEntity<DiagnosisType> updateDiagnosis(@Valid @RequestBody final DiagnosisType diagnosis) {
		this.diagnosisService.getDiagnosisById(diagnosis.getTypeId()).orElseThrow(
				() -> new ResourceNotFoundException("Not found DiagnosisType with id = " + diagnosis.getTypeId()));
		DiagnosisType diagType =this.diagnosisService.updateDiagnosisType(diagnosis);
		log.info("Diagnosis id: {} updated successfully", diagnosis.getTypeId());
		return new ResponseEntity<DiagnosisType>(diagType,HttpStatus.OK);
	}

	/**
	 * Gets all Diagnosis from patients table
	 * 
	 * @return Get All diagnosis present in Diagnosis table
	 */
	@Override
	@GetMapping("/getalldiagnosistype")
	public ResponseEntity<List<DiagnosisType>> getAllDiagnosisType() {
		List<DiagnosisType> diagType = this.diagnosisService.getAllDiagnosisType();
		if (diagType.isEmpty()) {
			log.error("Diagnosis List is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		log.info("Diagnosis List of size : {} returned successfully", diagType.size());
		return new ResponseEntity<List<DiagnosisType>>(diagType, HttpStatus.OK);
	}

	/**
	 * Get mapping that retrieves the detail of a specific diagnosis based on the is
	 * passed
	 * 
	 * @param - diagnosistypeid id of the diagnosis type
	 * @return - diagnosis type info of the id passed
	 */
	@Override
	@GetMapping("/getdiagnosistypebyid/{diagnosistypeid}")
	public ResponseEntity<DiagnosisType> getDiagnosis(@Valid @PathVariable("diagnosistypeid") Long diagnosistypeid) {
		DiagnosisType diagType = this.diagnosisService.getDiagnosisById(diagnosistypeid).orElseThrow(
				() -> new ResourceNotFoundException("Not found DiagnosisType with id = " + diagnosistypeid));
		return new ResponseEntity<DiagnosisType>(diagType, HttpStatus.OK);
	}

	/**
	 * Deletes a diagnosis based on diagnosis id
	 * 
	 * @param diagnosisId diagnosisType id
	 */
	@Override
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteDiagnosisType(@Valid @PathVariable("id") Long diagnosisTypeId) {
		this.diagnosisService.getDiagnosisById(diagnosisTypeId).orElseThrow(
				() -> new ResourceNotFoundException("Not found DiagnosisType with id = " + diagnosisTypeId));
		this.diagnosisService.delete(diagnosisTypeId);
		log.info("Diagnosis Type id: {} deleted successfully", diagnosisTypeId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

}
