package com.assignment.clinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.clinic.entity.DoctorPatient;
import com.assignment.clinic.entity.Patient;
import com.assignment.clinic.entity.PatientDiagnosis;
import com.assignment.clinic.exceptions.ResourceNotFoundException;
import com.assignment.clinic.repository.DoctorPatientRepository;
import com.assignment.clinic.repository.PatientDiagnosisRepository;
import com.assignment.clinic.repository.PatientRepository;
import com.assignment.clinic.serviceInterface.PatientService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Kavitha Service class for business logic related to patients
 * 
 *
 */
@Service
@Slf4j
@Transactional(rollbackOn = Throwable.class)
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PatientDiagnosisRepository patientDiagnosisRepository;

	@Autowired
	private DoctorPatientRepository doctorPatientRepository;

	/**
	 * save doctor info
	 * 
	 * @param patient Patient info to be created
	 */
	@Override
	public Patient savePatient(Patient patient) {
		log.info("Received Create Patient Request for Patient:{}", patient.getPatientId());
		try {
			return this.patientRepository.save(patient);
		} catch (Exception e) {
			log.error("Patient:{} could not be created {}", patient, e.getMessage());
		}
		return patient;
	}

	/**
	 * 
	 * Get All Patients present in Patients table
	 * 
	 */
	@Override
	public List<Patient> getAllPatients() {
		List<Patient> patientsList = patientRepository.findAll();
		log.info("Received Get All Patients Request of size {}", patientsList.size());
		if (patientsList.isEmpty()) {
			log.error("No Patients Exist {}", patientsList);
			throw new ResourceNotFoundException("Patient is empty");
		}
		return patientsList;
	}

	/**
	 * getting a specific record by using the method findById() of CrudRepository
	 * 
	 * @param id patient id
	 * @return Patient info associated with ID
	 */
	@Override
	public Optional<Patient> getPatientById(final Long id) {
		Optional<Patient> patient = this.patientRepository.findByPatientId(id);
		if (patient.isPresent()) {
			log.info("Received Get Patient Request for Patient id:{}", id);
			return patient;
		} else {
			throw new ResourceNotFoundException(
					"Patient with the mentioned id is empty");
		
		}
	}

	/**
	 * saving a specific record by using the method save() of CrudRepository
	 * 
	 * @param patient Patient info to be created
	 */
	@Override
	public Patient updatePatient(Patient patient) {
		List<PatientDiagnosis> list = new ArrayList<>();
		List<DoctorPatient> docPatList = new ArrayList<>();
		log.info("Received Update Patient Request for Id:{}", patient.getPatientId());
		Optional<Patient> oldPatientInfo = getPatientById(patient.getPatientId());
		Patient pat = oldPatientInfo.get();
		List<PatientDiagnosis> oldPatDiag = pat.getPatientDiagnosis();
		List<DoctorPatient> oldPatDoc = pat.getDoctorPatient();
		pat.setPatientName(patient.getPatientName());
		pat.setAddress(patient.getAddress());
		pat.setEmail(patient.getEmail());
		if (patient.getPatientDiagnosis() != null) {
			for (PatientDiagnosis patientDiagnosis : patient.getPatientDiagnosis()) {
				list.add(patientDiagnosis);
			}
			pat.setPatientDiagnosis(list);
		}
		if (patient.getDoctorPatient() != null) {
			for (DoctorPatient doctorPatient : patient.getDoctorPatient()) {
				docPatList.add(doctorPatient);
			}
			pat.setDoctorPatient(docPatList);
		}
		pat = this.patientRepository.save(pat);
		if (oldPatDiag != null && !oldPatDiag.isEmpty())
			this.patientDiagnosisRepository.deleteAll(oldPatDiag);
		if (oldPatDoc != null && !oldPatDoc.isEmpty())
			this.doctorPatientRepository.deleteAll(oldPatDoc);
		return pat;
	}

	/**
	 * deleting a specific record by using the method deleteById() of CrudRepository
	 * 
	 * @param id - patient to be deleted based on the id
	 */
	@Override
	public void delete(Long id) {
		Optional<Patient> pat = getPatientById(id);
		if (pat.isPresent()) {
			log.info("Received delete Patient Request for Id: {}", id);
			patientRepository.deleteByPatientId(id);
		} else {
			throw new RuntimeException();
		}
	}

}
