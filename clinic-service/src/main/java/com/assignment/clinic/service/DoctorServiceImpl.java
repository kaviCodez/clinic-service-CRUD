package com.assignment.clinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.clinic.entity.Doctor;
import com.assignment.clinic.entity.DoctorSpecialization;
import com.assignment.clinic.entity.DoctorPatient;
import com.assignment.clinic.entity.Patient;
import com.assignment.clinic.entity.PatientDiagnosis;
import com.assignment.clinic.exceptions.ResourceNotFoundException;
import com.assignment.clinic.repository.DoctorDiagnosisRepository;
import com.assignment.clinic.repository.DoctorRepository;
import com.assignment.clinic.repository.PatientRepository;
import com.assignment.clinic.serviceInterface.DoctorService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Kavitha Service class for business logic related to doctors
 * 
 *
 */
@Service
@Slf4j
@Transactional(rollbackOn = Throwable.class)
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DoctorDiagnosisRepository doctorDiagnosisRepository;

	@Autowired
	private PatientRepository patientRepository;

	/**
	 * save doctor info
	 * 
	 * @param doctor Doctor info to be created
	 * 
	 */
	@Override
	public Doctor saveDoctor(Doctor doctor) {
		log.info("Received Create Doctor Request for Doctor:{}", doctor.getDoctorId());
		try {
			return this.doctorRepository.save(doctor);
		} catch (Exception e) {
			log.error("Doctor:{} could not be created {}", doctor, e.getMessage());
		}
		return doctor;
	}

	/**
	 * 
	 * Get All Doctors present in Doctors table
	 * 
	 */
	@Override
	public List<Doctor> getAllDoctors() {
		List<Doctor> doctorsList = doctorRepository.findAll();
		 log.info("Received Get All Doctors Request of size {}",doctorsList.size());
		if (doctorsList.isEmpty()) {
			log.error("No Doctors Exist {}", doctorsList);
			throw new ResourceNotFoundException("Doctor is empty");
		}
		return doctorsList;
	}

	/**
	 * getting a specific record by using the method findById() of CrudRepository
	 * 
	 * @param id doctor id
	 * @return Doctor info associated with ID
	 */
	@Override
	public Optional<Doctor> getDoctorById(final Long id) {
		Optional<Doctor> doc = this.doctorRepository.findByDoctorId(id);
		if (doc.isPresent()) {
			log.info("Received Get Doctor Request for Doctor id:{}", id);
			return doc;
		} else {
			throw new ResourceNotFoundException(
					"Doctor with the mentioned id is empty");
		}
	}

	/**
	 * saving a specific record by using the method save() of CrudRepository
	 * 
	 * @param doctor Doctor info to be created
	 */
	public Doctor updateDoctor(Doctor doctor) {
		List<DoctorSpecialization> list = new ArrayList<>();
		log.info("Received Update Doctor Request for Id:{}", doctor.getDoctorId());
		Optional<Doctor> oldDoctorInfo = getDoctorById(doctor.getDoctorId());
		Doctor doc = oldDoctorInfo.get();
		List<DoctorSpecialization> oldDocDiag = doc.getDoctorSpecialization();
		doc.setDoctorName(doctor.getDoctorName());
		doc.setQualification(doctor.getQualification());
		if (doctor.getDoctorSpecialization() != null) {
			for (DoctorSpecialization doctorDiagnosis : doctor.getDoctorSpecialization()) {
				list.add(doctorDiagnosis);
			}
			doc.setDoctorSpecialization(list);
		}
		doc = this.doctorRepository.save(doc);
		if (oldDocDiag != null && !oldDocDiag.isEmpty())
			this.doctorDiagnosisRepository.deleteAll(oldDocDiag);
		return doc;
	}

	/**
	 * deleting a specific record by using the method deleteById() of CrudRepository
	 * 
	 * @param id - doctor to be deleted based on the id
	 */
	@Override
	public void delete(Long id) {
		Optional<Doctor> doc = getDoctorById(id);
		if (doc.isPresent()) {
			log.info("Received delete Doctor Request for Id: {}", id);
			doctorRepository.deleteByDoctorId(id);
		} else {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Get Doctor list based on patient's diagnosis type
	 * 
	 * @param - patient id for which the doctor specialist should be returned
	 * @return - Doctors List based on the patients diagnosis type
	 * 
	 */

	public List<Doctor> getDoctorsforpatient(final Long patientId) {
		List<Doctor> specialist = new ArrayList<>();
		if (patientId != null) {
			log.info("Received Get Doctor Request for Patient id:{}", patientId);
			Optional<Patient> patient = this.patientRepository.findByPatientId(patientId);
			List<String> types = new ArrayList<>();
			for (PatientDiagnosis patientDiagnosis : patient.get().getPatientDiagnosis()) {
				types.add(patientDiagnosis.getDiagType().getTypeName());
			}
			List<Doctor> allDoctor = this.doctorRepository.findAll();
			for (Doctor doctor : allDoctor) {
				for (DoctorSpecialization doctorDiagnosis : doctor.getDoctorSpecialization()) {
					for (String typeof : types) {
						if (doctorDiagnosis.getDiagType().getTypeName().equals(typeof)) {
							specialist.add(doctor);
						}
					}
				}
			}
		}
		return specialist;
	}
	
	/**
	 * Find all patients mapped to the doctor
	 * 
	 * @param- doctorId pass id of the doctor whose patient list should be displayed
	 * @return - Get all patients mapped to the doctor
	 * 
	 */

	public List<Patient> getAllPatientsforDoctor(Long doctorId) {
		log.info("Received Get all patients for Doctor id:{}", doctorId);
		List<Patient> getPatientsForDoctor = new ArrayList<>();
		List<Patient> allPatients = this.patientRepository.findAll();
		for (Patient patient : allPatients) {
			if (patient.getDoctorPatient() != null) {
				for (DoctorPatient doctorPatient : patient.getDoctorPatient()) {
					if (doctorPatient.getDoctor() != null && doctorPatient.getDoctor().getDoctorId() != null
							&& doctorPatient.getDoctor().getDoctorId() == doctorId) {
						getPatientsForDoctor.add(patient);
					}
				}
			}
		}
		return getPatientsForDoctor;
	}

	/**
	 * Delete All doctor entries from doctor table
	 */
	public void deleteAllDoctors() {
		log.info("Received delete request for all doctors in db");
		this.doctorRepository.deleteAll();
	}

}
