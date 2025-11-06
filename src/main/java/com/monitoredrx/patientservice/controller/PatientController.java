package com.monitoredrx.patientservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoredrx.patientservice.common.Messages;
import com.monitoredrx.patientservice.controller.common.RequestMappings;
import com.monitoredrx.patientservice.controller.dto.PatientAddressDto;
import com.monitoredrx.patientservice.controller.dto.request.CreateOrUpdatePatientRequest;
import com.monitoredrx.patientservice.controller.dto.response.GetPatientResponse;
import com.monitoredrx.patientservice.exception.ErrorResponse;
import com.monitoredrx.patientservice.exception.GlobalExceptionHandler;
import com.monitoredrx.patientservice.model.Patient;
import com.monitoredrx.patientservice.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class PatientController.
 */
@Slf4j
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(RequestMappings.PATIENTS_API)
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patient management APIs")
public class PatientController extends GlobalExceptionHandler {

	/** The service. */
	private final PatientService service;
    
    /**
     * Test api.
     */
    @GetMapping("/test")
    public void testApi () {
    	log.info("Test API....");
    }

    /**
     * Gets the all patients.
     *
     * @return the all patients
     */
    @Operation(summary = "Get all patients", responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        })
    @GetMapping
    public List<GetPatientResponse> getAllPatients() {
    	
    	log.info(Messages.GET_ALL_PATIENTS);
    	
    	List<Patient> patients = service.getAllPatients();
    	
    	List<GetPatientResponse> responseList = new ArrayList<>(patients.size());

    	patients.forEach(p -> {
    		PatientAddressDto address = new PatientAddressDto();
    		address.setAddress(p.getAddress());
    		address.setCity(p.getCity());
    		address.setState(p.getState());
    		address.setZipCode(p.getZipCode());
    		
			GetPatientResponse patientResponse = new GetPatientResponse(p.getId(), p.getFirstName(), p.getLastName(),
					p.getPhoneNumber(), p.getEmail(), address);
			
			responseList.add(patientResponse);
    	});
    	
    	
        return responseList;
    }

    /**
     * Gets the patient by id.
     *
     * @param id the id
     * @return the patient by id
     */
    @Operation(summary = "Get patient by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                )
        })
    @GetMapping("/{id}")
    public ResponseEntity<GetPatientResponse> getPatientById(@PathVariable("id") Long id) {
    	
    	log.info(Messages.GET_PATIENT_BY_ID, id);
    	
    	Patient patient = service.getPatientById(id);
    	PatientAddressDto address = new PatientAddressDto();
    	address.setAddress(patient.getAddress());
    	address.setCity(patient.getCity());
    	address.setState(patient.getState());
    	address.setZipCode(patient.getZipCode());
    	
		GetPatientResponse response = new GetPatientResponse(
				patient.getId(), 
				patient.getFirstName(),
				patient.getLastName(), 
				patient.getPhoneNumber(), 
				patient.getEmail(), 
				address);
    	
    	return ResponseEntity.ok(response);
    }

    /**
     * Creates the patient.
     *
     * @param CreatePatientRequest the create patient request
     * @return the response entity
     */
    @Operation(summary = "Create a new patient", responses = {
            @ApiResponse(responseCode = "201", description = "Patient created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                )
        })
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid CreateOrUpdatePatientRequest CreatePatientRequest) {
    	
    	log.info("Creating patient: {}", CreatePatientRequest);
    	
    	Patient createdPatient = service.createPatient(CreatePatientRequest);
    	
    	URI location = URI.create(RequestMappings.PATIENTS_API + createdPatient.getId());
    	
    	return ResponseEntity
                .created(location) // 201 Created
                .body(createdPatient);
    }

    /**
     * Update patient.
     *
     * @param id the id
     * @param updatePatientRequest the update patient request
     * @return the response entity
     */
    @Operation(summary = "Update patient by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Patient updated"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
        })
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") Long id, @RequestBody @Valid CreateOrUpdatePatientRequest updatePatientRequest) {
    	
    	log.info(Messages.UPDATE_PATIENT, id);
    	
        return ResponseEntity.ok(service.updatePatient(id, updatePatientRequest));
    }

    /**
     * Delete patient.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Delete patient by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Patient deleted"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
        })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
    	
    	log.info(Messages.DELETE_PATIENT, id);
    	
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    
}
