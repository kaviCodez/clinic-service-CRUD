package com.assignment.clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.clinic.entity.Doctor;

/**
 * 
 * @author Kavitha Repository Interface for Doctor CRUD operations
 *
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Optional<Doctor> findByDoctorId(Long id);

	void deleteByDoctorId(Long id);

	void save(Optional<Doctor> doctor);

}
