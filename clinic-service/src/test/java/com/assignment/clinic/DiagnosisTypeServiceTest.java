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

import com.assignment.clinic.entity.DiagnosisType;
import com.assignment.clinic.repository.DiagnosisTypeRepository;
import com.assignment.clinic.service.DiagnosisServiceTypeImpl;

/**
 * 
 * @author Kavitha Update,Get and delete TestCases for Diagnosis Type
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class DiagnosisTypeServiceTest {

	@Mock
	private DiagnosisTypeRepository diagnosisTypeRepository;

	@InjectMocks
	private DiagnosisServiceTypeImpl diagnosisServiceType;

	@Test
	public void updateDiagnosisType() {
		DiagnosisType diagType = new DiagnosisType();
		diagType.setTypeName("ortho");
		diagType.setTypeId(89L);

		DiagnosisType newDiagType = new DiagnosisType();
		newDiagType.setTypeName("ent");
		newDiagType.setTypeId(89L);

		given(diagnosisTypeRepository.findByTypeId(diagType.getTypeId())).willReturn(Optional.of(diagType));
		diagnosisServiceType.updateDiagnosisType(newDiagType);

		verify(diagnosisTypeRepository).save(newDiagType);
		verify(diagnosisTypeRepository).findByTypeId(newDiagType.getTypeId());
	}

	@Test(expected = RuntimeException.class)
	public void diagnosisTypeDoesNotexist() {

		DiagnosisType diagType = new DiagnosisType();
		diagType.setTypeName("ortho");
		diagType.setTypeId(89L);

		DiagnosisType newDiagType = new DiagnosisType();
		newDiagType.setTypeId(90L);
		newDiagType.setTypeName("ent");

		given(diagnosisTypeRepository.findByTypeId(anyLong())).willReturn(Optional.ofNullable(null));
		diagnosisServiceType.updateDiagnosisType(newDiagType);
	}
	// Delete Values from repository

	@Test
	public void deleteDiagnosisType() {
		DiagnosisType diagType = new DiagnosisType();
		diagType.setTypeName("ortho");
		diagType.setTypeId(1L);
		when(diagnosisTypeRepository.findByTypeId(diagType.getTypeId())).thenReturn(Optional.of(diagType));

		diagnosisServiceType.delete(diagType.getTypeId());
		verify(diagnosisTypeRepository).deleteByTypeId(diagType.getTypeId());
	}

	@Test(expected = RuntimeException.class)
	public void diagnosisTypeDoesntExist() {
		DiagnosisType diagType = new DiagnosisType();
		diagType.setTypeName("ortho");
		diagType.setTypeId(89L);

		given(diagnosisTypeRepository.findByTypeId(anyLong())).willReturn(Optional.ofNullable(null));
		diagnosisServiceType.delete(diagType.getTypeId());
	}

	// List All values
	@Test
	public void getAllDiagnosisTypes() {
		List<DiagnosisType> diagTypes = new ArrayList<>();
		diagTypes.add(new DiagnosisType());

		given(diagnosisTypeRepository.findAll()).willReturn(diagTypes);

		List<DiagnosisType> expected = diagnosisServiceType.getAllDiagnosisType();

		assertEquals(expected, diagTypes);
		verify(diagnosisTypeRepository).findAll();
	}

	// Detail Users

	@Test
	public void getDiagTypeById() {
		DiagnosisType diagType = new DiagnosisType();
		diagType.setTypeName("ortho");
		diagType.setTypeId(1L);

		when(diagnosisTypeRepository.findByTypeId(diagType.getTypeId())).thenReturn(Optional.of(diagType));

		Optional<DiagnosisType> expected = diagnosisServiceType.getDiagnosisById(diagType.getTypeId());

		assertThat(expected.get().getTypeName()).isSameAs(diagType.getTypeName());
		verify(diagnosisTypeRepository).findByTypeId(diagType.getTypeId());
	}

}