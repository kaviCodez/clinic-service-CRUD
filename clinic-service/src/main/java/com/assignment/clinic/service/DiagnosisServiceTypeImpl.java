package com.assignment.clinic.service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.clinic.entity.DiagnosisType;

import com.assignment.clinic.exceptions.ResourceNotFoundException;
import com.assignment.clinic.repository.DiagnosisTypeRepository;
import com.assignment.clinic.serviceInterface.DiagnosisServiceType;

/**
 * 
 * @author Kavitha Service class for business logic related to diagnosisType
 * 
 *
 */
@Service
@Slf4j
@Transactional(rollbackOn = Throwable.class)
public class DiagnosisServiceTypeImpl implements DiagnosisServiceType {

	@Autowired
	private DiagnosisTypeRepository diagnosisTypeRepository;

	/**
	 * save diagnosisType info
	 * @param diagnosisType DiagnosisType info to be created
	 * 
	 */
	@Override
	public DiagnosisType saveDiagnosisType(DiagnosisType diagnosisType) {
		log.info("Received Create DiagnosisType Request for DiagnosisType:{}", diagnosisType.getTypeId());
		try {
			return this.diagnosisTypeRepository.save(diagnosisType);
		} catch (Exception e) {
			log.error("DiagnosisType:{} could not be created {}", diagnosisType, e.getMessage());
		}
		return diagnosisType;
	}

	/**
	 * 
	 * Get All DiagnosisType present in DiagnosisType table
	 * 
	 */
	@Override
	public List<DiagnosisType> getAllDiagnosisType() {
		List<DiagnosisType> diagnosisTypeList = diagnosisTypeRepository.findAll();
		log.info("Received Get All Diagnosis Type Request {}", diagnosisTypeList);
		if (diagnosisTypeList.isEmpty()) {
			log.error("Diagnosis Type List is empty {}", diagnosisTypeList.size());
			throw new ResourceNotFoundException(
					"DiagnosisType is empty");
		}
		return diagnosisTypeList;
	}

	/**
	 * getting a specific record by using the method findById() of CrudRepository
	 * 
	 * @param id diagnosis id
	 * @return Diagnosis Type info associated with ID
	 */
	@Override
	public Optional<DiagnosisType> getDiagnosisById(final Long id) {
		Optional<DiagnosisType> diagnosis = this.diagnosisTypeRepository.findByTypeId(id);
		if (diagnosis.isPresent()) {
			log.info("Received Get DiagnosisType Request for Diagnosis Type id:{}", id);
			return diagnosis;
		} else {
			throw new ResourceNotFoundException(
					"DiagnosisType with the mentioned id is empty");
		}
	}

	/**
	 * saving a specific record by using the method save() of CrudRepository
	 * 
	 * @param DiagnosisType diagnosisType info to be created
	 */
	@Override
	public DiagnosisType updateDiagnosisType(DiagnosisType diagnosisType) {
		Optional<DiagnosisType> diagnosisId = getDiagnosisById(diagnosisType.getTypeId());
		if (diagnosisId.isPresent()) {
			log.info("Received Update Diagnosis Type Request for Id:{}", diagnosisType);
			return diagnosisTypeRepository.save(diagnosisType);
		} else {
			throw new ResourceNotFoundException(
					"DiagnosisType with the mentioned id is empty");
		}
	}

	/**
	 * deleting a specific record by using the method deleteById() of CrudRepository
	 * 
	 * @param id - diagnosisType to be deleted based on the id
	 */
	@Override
	public void delete(Long id) {
		Optional<DiagnosisType> diagnosisId = getDiagnosisById(id);
		if (diagnosisId.isPresent()) {
			log.info("Received delete DiagnosisType Request for Id: {}", id);
			diagnosisTypeRepository.deleteByTypeId(id);
		} else {
			throw new ResourceNotFoundException(
					"DiagnosisType with the mentioned id is empty");
		}
	}

}
