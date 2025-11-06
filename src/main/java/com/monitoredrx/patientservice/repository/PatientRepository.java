package com.monitoredrx.patientservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monitoredrx.patientservice.model.Patient;

/**
 * The Interface PatientRepository.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

	/**
	 * Find by deleted.
	 *
	 * @param deleted the deleted
	 * @return the list
	 */
	List<Patient> findByDeleted(Boolean deleted);
	
	/**
	 * Find by id and deleted.
	 *
	 * @param id the id
	 * @param deleted the deleted
	 * @return the patient
	 */
	Patient findByIdAndDeleted(Long id, Boolean deleted);
	
	/**
	 * Find by email and deleted.
	 *
	 * @param email the email
	 * @param deleted the deleted
	 * @return the patient
	 */
	Patient findByEmailAndDeleted(String email, Boolean deleted);
	
	/**
	 * Find by phone number and deleted.
	 *
	 * @param phoneNumber the phone number
	 * @param deleted the deleted
	 * @return the patient
	 */
	Patient findByPhoneNumberAndDeleted(String phoneNumber, Boolean deleted);
}
