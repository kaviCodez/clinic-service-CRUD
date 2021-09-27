package com.assignment.clinic.entity;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Kavitha Entity class for Patient info
 *
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@patientId")
public class Patient extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long patientId;
	private String patientName;
	private String address;
	
	@Email(message = "Email should be valid")
    private String email;

	@OneToMany(targetEntity = PatientDiagnosis.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_Id", referencedColumnName = "patientId", foreignKey = @ForeignKey(name = "fk_patId"))
	private List<PatientDiagnosis> patientDiagnosis;

	@OneToMany(targetEntity = DoctorPatient.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_Id", referencedColumnName = "patientId", foreignKey = @ForeignKey(name = "fk_patdocId"))
	private List<DoctorPatient> doctorPatient;
}
