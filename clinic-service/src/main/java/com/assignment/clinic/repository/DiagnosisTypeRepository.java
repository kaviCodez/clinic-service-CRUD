package com.assignment.clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.clinic.entity.DiagnosisType;

/**
 * 
 * @author Kavitha Repository Interface for DiagnosisType CRUD operations
 *
 */
@Repository
public interface DiagnosisTypeRepository extends JpaRepository<DiagnosisType, Long> {
	Optional<DiagnosisType> findByTypeId(Long id);

	void deleteByTypeId(Long id);

	void save(Optional<DiagnosisType> diagnosisType);

	void findByTypeName(String expected);
}
