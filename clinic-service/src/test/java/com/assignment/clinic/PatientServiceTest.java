package com.assignment.clinic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.assignment.clinic.entity.Patient;
import com.assignment.clinic.repository.PatientRepository;
import com.assignment.clinic.service.DoctorServiceImpl;
import com.assignment.clinic.service.PatientServiceImpl;

/**
 * 
 * @author Kavitha Update,Get and delete TestCases for Patient
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private DoctorServiceImpl doctorService;

	@InjectMocks
	private PatientServiceImpl patientService;

	@Test
	public void updatePatientType() {
		Patient patient = new Patient();
		patient.setPatientName("pat");
		patient.setAddress("cbe");
		patient.setPatientId(89L);

		Patient newPatient = new Patient();
		newPatient.setPatientName("patient1");
		newPatient.setAddress("cbe");
		newPatient.setPatientId(89L);

		given(patientRepository.findByPatientId(patient.getPatientId())).willReturn(Optional.of(patient));
		patientService.updatePatient(newPatient);

		verify(patientRepository).findByPatientId(newPatient.getPatientId());
	}

	@Test(expected = RuntimeException.class)
	public void patientDoesNotexist() {

		Patient patient = new Patient();
		patient.setPatientName("patient1");
		patient.setAddress("cbe");
		patient.setPatientId(89L);

		Patient newPatient = new Patient();
		newPatient.setPatientName("patient2");
		newPatient.setAddress("cbe");
		newPatient.setPatientId(90L);

		given(patientRepository.findByPatientId(anyLong())).willReturn(null);
		patientService.updatePatient(newPatient);
	}
	// Delete Values from repository

	@Test
	public void deletePatientType() {
		Patient patient = new Patient();
		patient.setPatientName("patient1");
		patient.setAddress("cbe");
		patient.setPatientId(1L);
		// deleteDiagnosisTypeService.saveDiagnosisType(diagType);
		when(patientRepository.findByPatientId(patient.getPatientId())).thenReturn(Optional.of(patient));

		patientService.delete(patient.getPatientId());
		verify(patientRepository).deleteByPatientId(patient.getPatientId());
	}

	@Test(expected = RuntimeException.class)
	public void patientDoesntExist() {
		Patient patient = new Patient();
		patient.setPatientName("patient1");
		patient.setAddress("cbe");
		patient.setPatientId(89L);

		given(patientRepository.findByPatientId(anyLong())).willReturn(null);
		patientService.delete(patient.getPatientId());
	}

	// List All values
	@Test
	public void getAllPatient() {
		List<Patient> patients = new ArrayList<>();
		patients.add(new Patient());

		given(patientRepository.findAll()).willReturn(patients);

		List<Patient> expected = patientService.getAllPatients();

		assertEquals(expected, patients);
		verify(patientRepository).findAll();
	}

	// get value based on id

	@Test
	public void getPatientById() {
		Patient patient = new Patient();
		patient.setPatientName("patient1");
		patient.setAddress("cbe");
		patient.setPatientId(1L);

		when(patientRepository.findByPatientId(patient.getPatientId())).thenReturn(Optional.of(patient));

		Optional<Patient> expected = patientService.getPatientById(patient.getPatientId());

		assertThat(expected.get().getPatientName()).isSameAs(patient.getPatientName());
		verify(patientRepository).findByPatientId(patient.getPatientId());
	}

}