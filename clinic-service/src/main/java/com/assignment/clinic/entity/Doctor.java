package com.assignment.clinic.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Kavitha Doctor Entity class for persisting doctor info Mapping of
 *         doctor based on specialist type Mapping of doctor based on patients
 *         diagnosis type
 *
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@doctorId")
public class Doctor extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long doctorId;
	private String doctorName;
	private String qualification;
	private String hospitalName;

	@OneToMany(targetEntity = DoctorSpecialization.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "doctor_Id", referencedColumnName = "doctorId", foreignKey = @ForeignKey(name = "fk_docId"))
	private List<DoctorSpecialization> doctorSpecialization;

	@JsonIgnore
	@OneToMany(targetEntity = DoctorPatient.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "doctor_Id", referencedColumnName = "doctorId", foreignKey = @ForeignKey(name = "fk_docpatId"))
	private List<DoctorPatient> doctorPatient;

}
