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

import com.assignment.clinic.controller.DoctorControllerImpl;
import com.assignment.clinic.entity.Doctor;
import com.assignment.clinic.service.DoctorServiceImpl;
import com.assignment.clinic.util.JsonUtil;

/**
 * 
 * @author Kavitha
 * Test Case for Create Doctor
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(DoctorControllerImpl.class)
public class DoctorControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorServiceImpl service;

	@Test
	public void createDoctorPostMethod() throws Exception {

		Doctor doctor = new Doctor();
		doctor.setDoctorName("doc1");
		doctor.setQualification("mbbs");

		given(service.saveDoctor(doctor)).willReturn(doctor);

		mockMvc.perform(
				post("/doctors/createdoctor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(doctor)))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
    public void listAllDoctors()
            throws Exception {
		Doctor doctor = new Doctor();
		doctor.setDoctorName("doc1");
		doctor.setQualification("mbbs");

        List<Doctor> doctors = Arrays.asList(doctor);

        given(service
                .getAllDoctors())
                .willReturn(doctors);

        mockMvc.perform(get("/doctors/getalldoctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(doctors.size())))
                .andExpect(jsonPath("$[0].doctorName", is(doctor.getDoctorName())));
    }
}
