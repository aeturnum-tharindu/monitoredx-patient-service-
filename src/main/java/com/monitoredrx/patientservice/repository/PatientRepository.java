package com.monitoredrx.patientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monitoredrx.patientservice.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient findByIdAndDeleted(Long id, Boolean deleted);
	
	Patient findByEmailAndDeleted(String email, Boolean deleted);
	
	Patient findByPhoneNumberAndDeleted(String phoneNumber, Boolean deleted);
}
