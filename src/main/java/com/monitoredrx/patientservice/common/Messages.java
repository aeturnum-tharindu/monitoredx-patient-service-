package com.monitoredrx.patientservice.common;

public interface Messages {

	// info
	String GET_ALL_PATIENTS = "Fetching all patients";
	
	String GET_PATIENT_BY_ID = "Fetching patient with id: {}";
	
	String CREATE_PATIENT = "Creating patient: {}";
	
	String UPDATE_PATIENT = "Updating patient with id: {}";
	
	String DELETE_PATIENT = "Deleting patient with id: {}";
	
	// error
	String PATIENT_NOT_FOUND = "Patient not found";
	
	String PATIENT_EMAIL_ALREADY_EXIST = "Patient email already exists";
	
	String PATIENT_PHONE_ALREADY_EXIST = "Patient phone number already exists";
	
	// error for log messages
	String PATIENT_EMAIL_ALREADY_EXIST_LOG_MSG = "Patient email '%s' already exists";
	
	String PATIENT_PHONE_ALREADY_EXIST_LOG_MSG = "Patient phone '%s' already exists";
	
}
