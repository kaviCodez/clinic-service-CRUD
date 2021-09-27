package com.assignment.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 
 * @author Kavitha Diagnosis type class for mapping doctors based in
 *         specialization Patients based on illness Both specialization and
 *         illness are considered as type diagnosis
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DiagnosisType extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long typeId;

	@Valid
	@NotNull
	@Column(unique=true)
	private String typeName;

	@JsonBackReference
	@OneToMany(mappedBy = "doctor")
	private List<DoctorSpecialization> doctorSpecialization;

	@JsonBackReference(value = "jsonref")
	@OneToMany(mappedBy = "patient")
	private List<PatientDiagnosis> patientDiagnosis;

}
