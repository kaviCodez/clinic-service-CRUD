package com.assignment.clinic.serviceInterface;

import java.util.List;

import java.util.Optional;

import com.assignment.clinic.entity.DiagnosisType;

/**
 * 
 * @author Kavitha Interface for diagnosis type
 *
 */

public interface DiagnosisServiceType {

	DiagnosisType saveDiagnosisType(DiagnosisType diagnosisType);

	List<DiagnosisType> getAllDiagnosisType();

	Optional<DiagnosisType> getDiagnosisById(Long id);

	DiagnosisType updateDiagnosisType(DiagnosisType diagnosisType);

	void delete(Long id);
}
