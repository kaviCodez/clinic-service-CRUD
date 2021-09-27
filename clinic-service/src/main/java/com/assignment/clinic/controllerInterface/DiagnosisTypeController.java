package com.assignment.clinic.controllerInterface;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.assignment.clinic.entity.DiagnosisType;

/**
 * 
 * @author Kavitha Interface for diagnosisTypeController
 *
 */

public interface DiagnosisTypeController {

	ResponseEntity<DiagnosisType> saveDiagnosisType(DiagnosisType diagnosisType);

	ResponseEntity<DiagnosisType> updateDiagnosis(DiagnosisType diagnosis);

	ResponseEntity<List<DiagnosisType>> getAllDiagnosisType();

	ResponseEntity<DiagnosisType> getDiagnosis(Long diagnosistypeid);

	ResponseEntity<Void> deleteDiagnosisType(Long diagnosisTypeId);

}
