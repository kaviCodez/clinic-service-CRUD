package com.assignment.clinic.controllerTests;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.clinic.controller.PatientControllerImpl;
import com.assignment.clinic.entity.Patient;
import com.assignment.clinic.service.PatientServiceImpl;
import com.assignment.clinic.util.JsonUtil;

/**
 * 
 * @author Kavitha
 * Test Case for Create Patient
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(PatientControllerImpl.class)
public class PatientControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientServiceImpl service;

	@Test
	public void createPatientPostMethod() throws Exception {

		Patient patient = new Patient();
		patient.setPatientName("patient1");
		patient.setAddress("cbe");

		given(service.savePatient(patient)).willReturn(patient);

		mockMvc.perform(post("/patients/createpatient").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(patient))).andExpect(status().is2xxSuccessful());
	}
	
	@Test
    public void listAllPatients()
            throws Exception {
		Patient patient = new Patient();
		patient.setPatientName("patient1");
		patient.setAddress("cbe");

        List<Patient> patients = Arrays.asList(patient);

        given(service
                .getAllPatients())
                .willReturn(patients);

        mockMvc.perform(get("/patients/getallpatients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(patients.size())))
                .andExpect(jsonPath("$[0].patientName", is(patient.getPatientName())));
    }
}
