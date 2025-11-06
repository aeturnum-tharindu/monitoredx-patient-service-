package com.monitoredrx.patientservice.common;

// TODO: Auto-generated Javadoc
/**
 * The Interface Messages.
 */
public interface Messages {

	// info
	/** The get all patients. */
	String GET_ALL_PATIENTS = "Fetching all patients";
	
	/** The get patient by id. */
	String GET_PATIENT_BY_ID = "Fetching patient with id: {}";
	
	/** The create patient. */
	String CREATE_PATIENT = "Creating patient: {}";
	
	/** The update patient. */
	String UPDATE_PATIENT = "Updating patient with id: {}";
	
	/** The delete patient. */
	String DELETE_PATIENT = "Deleting patient with id: {}";
	
	/** The patient not found. */
	// error
	String PATIENT_NOT_FOUND = "Patient not found";
	
	/** The patient email already exist. */
	String PATIENT_EMAIL_ALREADY_EXIST = "Patient email already exists";
	
	/** The patient phone already exist. */
	String PATIENT_PHONE_ALREADY_EXIST = "Patient phone number already exists";

	// error for log messages
	/** The patient not found log msg. */
	String PATIENT_NOT_FOUND_LOG_MSG = "Patient not exist with id '%s' ";
	
	/** The patient email already exist log msg. */
	String PATIENT_EMAIL_ALREADY_EXIST_LOG_MSG = "Patient email '%s' already exists";
	
	/** The patient phone already exist log msg. */
	String PATIENT_PHONE_ALREADY_EXIST_LOG_MSG = "Patient phone '%s' already exists";
	
}
