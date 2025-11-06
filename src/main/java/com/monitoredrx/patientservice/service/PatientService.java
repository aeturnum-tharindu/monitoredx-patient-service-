package com.monitoredrx.patientservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monitoredrx.patientservice.common.Messages;
import com.monitoredrx.patientservice.controller.dto.request.CreateOrUpdatePatientRequest;
import com.monitoredrx.patientservice.exception.DataAlreadyExistsException;
import com.monitoredrx.patientservice.exception.EntityNotFoundException;
import com.monitoredrx.patientservice.model.Patient;
import com.monitoredrx.patientservice.repository.PatientRepository;

import lombok.AllArgsConstructor;

/**
 * The Class PatientService.
 */
@Service
@AllArgsConstructor
public class PatientService {

	/** The patient repository. */
	private final PatientRepository patientRepository;

    /**
     * Gets the all patients.
     *
     * @return the all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findByDeleted(false);
    }

    /**
     * Gets the patient by id.
     *
     * @param id the id
     * @return the patient by id
     */
    public Patient getPatientById(Long id) {
    	
    	Patient patient = patientRepository.findByIdAndDeleted(id, false);
    	
    	if (patient == null) {
    		// throw exception
    		throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND, Messages.PATIENT_NOT_FOUND_LOG_MSG.formatted(id));
    	}
    	
        return patient;
    }

    /**
     * Creates the patient.
     *
     * @param createPatientRequest the create patient request
     * @return the patient
     */
    public Patient createPatient(CreateOrUpdatePatientRequest createPatientRequest) {
    	
    	validateCreatePatientRequest(createPatientRequest);
    	
        return patientRepository.save(transformCreateOrUpdateReqToPatient(createPatientRequest));
    }
    
    /**
     * Validate create patient request.
     *
     * @param request the request
     */
    private void validateCreatePatientRequest(CreateOrUpdatePatientRequest request) {
    	
    	Patient patientByEmail = patientRepository.findByEmailAndDeleted(request.getEmail(), false);
    	if (patientByEmail != null) {
    		throw new DataAlreadyExistsException(Messages.PATIENT_EMAIL_ALREADY_EXIST, Messages.PATIENT_EMAIL_ALREADY_EXIST_LOG_MSG.formatted(request.getEmail()));
    	}
    	
    	Patient patientByPhone = patientRepository.findByPhoneNumberAndDeleted(request.getPhoneNumber(), false);
    	if (patientByPhone != null) {
    		throw new DataAlreadyExistsException(Messages.PATIENT_PHONE_ALREADY_EXIST, Messages.PATIENT_PHONE_ALREADY_EXIST_LOG_MSG.formatted(request.getPhoneNumber()));
    	}
    }

	/**
	 * Transform create or update req to patient.
	 *
	 * @param request the request
	 * @return the patient
	 */
	private Patient transformCreateOrUpdateReqToPatient(CreateOrUpdatePatientRequest request) {
		// transform request to domain dto.
		Patient patient = new Patient();
		patient.setFirstName(request.getFirstName());
		patient.setLastName(request.getLastName());
		patient.setEmail(request.getEmail());
		patient.setPhoneNumber(request.getPhoneNumber());
		
		if (request.getAddress() != null) {
			patient.setAddress(request.getAddress().getAddress());
			patient.setCity(request.getAddress().getCity());
			patient.setState(request.getAddress().getState());
			patient.setZipCode(request.getAddress().getZipCode());
		}
		
		return patient;
	}
    
    /**
     * Update patient.
     *
     * @param id the id
     * @param updatePatientRequest the update patient request
     * @return the patient
     */
    public Patient updatePatient(Long id, CreateOrUpdatePatientRequest updatePatientRequest) {
    	
    	// check if exist
    	Patient patientToUpdate = getPatientById(id);
    	validateUpdatePatient(id, updatePatientRequest);
    	
    	patientToUpdate.setFirstName(updatePatientRequest.getFirstName());
    	patientToUpdate.setLastName(updatePatientRequest.getLastName());
    	patientToUpdate.setEmail(updatePatientRequest.getEmail());
    	patientToUpdate.setPhoneNumber(updatePatientRequest.getPhoneNumber());
		
		if (updatePatientRequest.getAddress() != null) {
			patientToUpdate.setAddress(updatePatientRequest.getAddress().getAddress());
			patientToUpdate.setCity(updatePatientRequest.getAddress().getCity());
			patientToUpdate.setState(updatePatientRequest.getAddress().getState());
			patientToUpdate.setZipCode(updatePatientRequest.getAddress().getZipCode());
		}
    	
        return patientRepository.save(patientToUpdate);
    }
    
    /**
     * Validate update patient.
     *
     * @param id the id
     * @param request the request
     */
    private void validateUpdatePatient(Long id, CreateOrUpdatePatientRequest request) {
    	
    	Patient patientByEmail = patientRepository.findByEmailAndDeleted(request.getEmail(), false);
    	if (patientByEmail != null && patientByEmail.getId() != id) {
    		throw new DataAlreadyExistsException(Messages.PATIENT_EMAIL_ALREADY_EXIST, "Patient email '%s' already exists".formatted(request.getEmail()));
    	}
    	
    	Patient patientByPhone = patientRepository.findByPhoneNumberAndDeleted(request.getPhoneNumber(), false);
    	if (patientByPhone != null && patientByPhone.getId() != id) {
    		throw new DataAlreadyExistsException(Messages.PATIENT_PHONE_ALREADY_EXIST, "Patient phone '%s' already exists".formatted(request.getPhoneNumber()));
    	}
    }

    /**
     * Delete patient.
     *
     * @param id the id
     */
    public void deletePatient(Long id) {
    	
    	Patient patient = getPatientById(id);
    	patient.setDeleted(true);
        patientRepository.save(patient);
    }
    
}
