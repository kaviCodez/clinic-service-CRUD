package com.assignment.clinic.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Kavitha Entity class for mapping patient with the diagnosis type
 *
 */

@Entity
@Getter
@Setter
public class PatientDiagnosis extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long patientDiagId;

	@JsonBackReference(value = "jsonref")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_Id")
	private Patient patient;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "typeId")
	private DiagnosisType diagType;

}
