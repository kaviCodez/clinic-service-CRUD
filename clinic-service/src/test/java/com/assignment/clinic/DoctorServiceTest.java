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

import com.assignment.clinic.entity.Doctor;
import com.assignment.clinic.repository.DoctorRepository;
import com.assignment.clinic.repository.PatientRepository;
import com.assignment.clinic.service.DoctorServiceImpl;

/**
 * 
 * @author Kavitha Update,Get and delete TestCases for Doctor
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class DoctorServiceTest {

	@Mock
	private DoctorRepository doctorRepository;

	@InjectMocks
	private DoctorServiceImpl doctorService;

	@Mock
	private PatientRepository patientRepository;

	@Test
	public void updateDoctorType() {
		Doctor doctor = new Doctor();
		doctor.setDoctorName("docTest");
		doctor.setQualification("mbbs");
		doctor.setDoctorId(89L);

		Doctor newDoctor = new Doctor();
		newDoctor.setDoctorName("doc122");
		newDoctor.setQualification("mbbs");
		newDoctor.setDoctorId(89L);

		given(doctorRepository.findByDoctorId(doctor.getDoctorId())).willReturn(Optional.of(doctor));
		doctorService.updateDoctor(newDoctor);
		verify(doctorRepository).findByDoctorId(newDoctor.getDoctorId());
	}

	@Test(expected = RuntimeException.class)
	public void doctorDoesNotexist() {

		Doctor doctor = new Doctor();
		doctor.setDoctorName("doc1");
		doctor.setQualification("mbbs");
		doctor.setDoctorId(89L);

		Doctor newDoctor = new Doctor();
		newDoctor.setDoctorName("doc1");
		newDoctor.setQualification("mbbs");
		newDoctor.setDoctorId(90L);

		given(doctorRepository.findByDoctorId(anyLong())).willReturn(Optional.ofNullable(null));
		doctorService.updateDoctor(newDoctor);
	}
	// Delete Values from repository

	@Test
	public void deleteDoctorType() {
		Doctor doctor = new Doctor();
		doctor.setDoctorName("doc1");
		doctor.setQualification("mbbs");
		doctor.setDoctorId(1L);
		when(doctorRepository.findByDoctorId(doctor.getDoctorId())).thenReturn(Optional.of(doctor));

		doctorService.delete(doctor.getDoctorId());
		verify(doctorRepository).deleteByDoctorId(doctor.getDoctorId());
	}

	@Test(expected = RuntimeException.class)
	public void doctorDoesntExist() {
		Doctor doctor = new Doctor();
		doctor.setDoctorName("doc1");
		doctor.setQualification("mbbs");
		doctor.setDoctorId(89L);

		given(doctorRepository.findByDoctorId(anyLong())).willReturn(Optional.ofNullable(null));
		doctorService.delete(doctor.getDoctorId());
	}

	// List All values
	@Test
	public void getAllDoctor() {
		List<Doctor> doctors = new ArrayList<>();
		doctors.add(new Doctor());

		given(doctorRepository.findAll()).willReturn(doctors);

		List<Doctor> expected = doctorService.getAllDoctors();

		assertEquals(expected, doctors);
		verify(doctorRepository).findAll();
	}

	// Detail Users

	@Test
	public void getDoctorById() {
		Doctor doctor = new Doctor();
		doctor.setDoctorName("doc1");
		doctor.setQualification("mbbs");
		doctor.setDoctorId(89L);

		when(doctorRepository.findByDoctorId(doctor.getDoctorId())).thenReturn(Optional.of(doctor));

		Optional<Doctor> expected = doctorService.getDoctorById(doctor.getDoctorId());

		assertThat(expected.get().getDoctorName()).isSameAs(doctor.getDoctorName());
		verify(doctorRepository).findByDoctorId(doctor.getDoctorId());
	}

}