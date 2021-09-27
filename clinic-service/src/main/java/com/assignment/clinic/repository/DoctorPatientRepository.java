package com.assignment.clinic.repository;

/**
 * @author Kavitha Doctor Patient repository for doctorPatient table CRUD operations
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.clinic.entity.DoctorPatient;

public interface DoctorPatientRepository extends JpaRepository<DoctorPatient, Long> {

}
