package com.assignment.clinic.controllerTests;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.assignment.clinic.controller.DiagnosisTypeControllerImpl;
import com.assignment.clinic.entity.DiagnosisType;
import com.assignment.clinic.service.DiagnosisServiceTypeImpl;

/**
 * 
 * @author Kavitha Test Case for Create Diagnosis type
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DiagnosisTypeControllerImpl.class)
public class DiagnosisTypeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DiagnosisServiceTypeImpl service;

	@Test
	public void listAllDiagnosisType() throws Exception {

		DiagnosisType diagType = new DiagnosisType();
		diagType.setTypeName("ortho");

		List<DiagnosisType> allDiagTypes = Arrays.asList(diagType);

		given(service.getAllDiagnosisType()).willReturn(allDiagTypes);

		mockMvc.perform(get("/diagnosistype/getalldiagnosistype").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(allDiagTypes.size())))
				.andExpect(jsonPath("$[0].typeName", is(diagType.getTypeName())));
	}

}
