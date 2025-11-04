package com.monitoredrx.patientservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.monitoredrx.patientservice.controller.common.RequestMappings;
import com.monitoredrx.patientservice.controller.dto.PatientAddressDto;
import com.monitoredrx.patientservice.controller.dto.request.CreateOrUpdatePatientRequest;
import com.monitoredrx.patientservice.exception.DataAlreadyExistsException;
import com.monitoredrx.patientservice.exception.EntityNotFoundException;
import com.monitoredrx.patientservice.model.Patient;
import com.monitoredrx.patientservice.service.PatientService;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;
    
    private Patient patient;

    @BeforeEach
    void setUp() {
    	patient = new Patient();
    	patient.setId(1L);
    	patient.setFirstName("John");
    	patient.setLastName("Doe");
    	patient.setState("NY");
    	
    	patient.setCreatedDate(new Date());
    	patient.setModifiedDate(new Date());
    }
    
    @DisplayName("Get Patients API")
	@Nested
	@Order(1)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class GetPatientsApiTest {

    	@Test
        void shouldReturnAllPatients() throws Exception {
        	
        	List<Patient> mockPatients = List.of(patient);
            Mockito.when(patientService.getAllPatients()).thenReturn(mockPatients);

            mockMvc.perform(get(RequestMappings.PATIENTS_API))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].firstName").value("John"));
        }
	
    }
    
    @DisplayName("Get Patient by Id API")
	@Nested
	@Order(2)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class GetPatientApiTest {
    	
    	@Test
        void shouldReturnPatientById() throws Exception {
        	
        	Mockito.when(patientService.getPatientById(1L)).thenReturn(patient);

            mockMvc.perform(get(RequestMappings.PATIENTS_API + "/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("John"));
        }
    	
    	@Test
        void shouldReturnBadRequestWhenPatientNotFound() throws Exception {

    		Mockito.when(patientService.getPatientById(1L))
            .thenThrow(new EntityNotFoundException("Patient not found"));
    		
            mockMvc.perform(get(RequestMappings.PATIENTS_API + "/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Patient not found"));
        }
    }
    
    @DisplayName("Create Patient API")
	@Nested
	@Order(3)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class CreatePatientApiTest {
    
    	@Test
        void shouldCreatePatient() throws Exception {
        	
        	CreateOrUpdatePatientRequest createPatientRequest = new CreateOrUpdatePatientRequest();
        	createPatientRequest.setFirstName("John");
        	createPatientRequest.setLastName("Doe");
        	createPatientRequest.setPhoneNumber("1234-1234");
        	createPatientRequest.setEmail("test@abc.com");
        	
        	PatientAddressDto address = new PatientAddressDto();
        	address.setState("NC");
        	createPatientRequest.setAddress(address);
        	
    		Mockito.when(patientService.createPatient(any(CreateOrUpdatePatientRequest.class)))
    				.thenReturn(transformCreateRequestToDomain(createPatientRequest));

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(post(RequestMappings.PATIENTS_API)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName").value("John"));
        }
    	
    	@Test
        void shouldReturnBadRequestWhenFirstNameMissing() throws Exception {

            String json = """
                    {
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(post(RequestMappings.PATIENTS_API)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    	
    	@Test
        void shouldReturnBadRequestWhenPhoneMissing() throws Exception {

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(post(RequestMappings.PATIENTS_API)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    	
    	@Test
        void shouldReturnBadRequestWhenEmailMissing() throws Exception {

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234"
                    }
                    """;

            mockMvc.perform(post(RequestMappings.PATIENTS_API)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    	
    	@Test
        void shouldReturnBadRequestWhenPhoneAlreadyExists() throws Exception {
            
    		when(patientService.createPatient(any(CreateOrUpdatePatientRequest.class)))
                    .thenThrow(new DataAlreadyExistsException("Patient phone number already exists"));

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(post(RequestMappings.PATIENTS_API)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Patient phone number already exists"));
        }
    	
    	@Test
        void shouldReturnBadRequestWhenEmailAlreadyExists() throws Exception {
            
    		when(patientService.createPatient(any(CreateOrUpdatePatientRequest.class)))
                    .thenThrow(new DataAlreadyExistsException("Patient email already exists"));

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(post(RequestMappings.PATIENTS_API)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Patient email already exists"));
        }
    }
    
    @DisplayName("Update Patient API")
	@Nested
	@Order(4)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class UpdatePatientApiTest {
    
    	@Test
        void shouldUpdatePatient() throws Exception {
        	
        	CreateOrUpdatePatientRequest createPatientRequest = new CreateOrUpdatePatientRequest();
        	createPatientRequest.setFirstName("John");
        	createPatientRequest.setLastName("Doe");
        	createPatientRequest.setPhoneNumber("1234-1234");
        	createPatientRequest.setEmail("test@abc.com");
        	
        	PatientAddressDto address = new PatientAddressDto();
        	address.setState("NC");
        	createPatientRequest.setAddress(address);
        	
    		Mockito.when(patientService.updatePatient(any(Long.class), any(CreateOrUpdatePatientRequest.class)))
    				.thenReturn(transformCreateRequestToDomain(createPatientRequest));

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("John"));
        }
    	
    	@Test
        void shouldReturnBadRequestWhenPatientNotFound() throws Exception {

    		Mockito.when(patientService.updatePatient(any(Long.class), any(CreateOrUpdatePatientRequest.class)))
    				.thenThrow(new EntityNotFoundException("Patient not found"));;
    		
            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Patient not found"));
        }
    	
    	@Test
        void shouldReturnBadRequestWhenFirstNameMissing() throws Exception {

            String json = """
                    {
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    	
    	@Test
        void shouldReturnBadRequestWhenPhoneMissing() throws Exception {

            String json = """
                    {
            		  "first_name": "John",  
                      "last_name": "Doe",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    	
    	@Test
        void shouldReturnBadRequestWhenEmailMissing() throws Exception {

            String json = """
                    {
            		  "first_name": "John",  
                      "last_name": "Doe",
                      "phone_number": "1234-1234
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }
    	
    	@Test
        void shouldReturnBadRequestWhenPhoneAlreadyExists() throws Exception {
            
    		when(patientService.updatePatient(any(Long.class), any(CreateOrUpdatePatientRequest.class)))
                    .thenThrow(new DataAlreadyExistsException("Patient phone number already exists"));

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Patient phone number already exists"));
        }
    	
    	@Test
        void shouldReturnBadRequestWhenEmailAlreadyExists() throws Exception {
            
    		when(patientService.updatePatient(any(Long.class), any(CreateOrUpdatePatientRequest.class)))
                    .thenThrow(new DataAlreadyExistsException("Patient email already exists"));

            String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "phone_number": "1234-1234",
            		  "email": "test@abc.com"
                    }
                    """;

            mockMvc.perform(put(RequestMappings.PATIENTS_API + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Patient email already exists"));
        }
    }
    
    private Patient transformCreateRequestToDomain(CreateOrUpdatePatientRequest request) {
    	
    	Patient patient = new Patient();
    	patient.setFirstName(request.getFirstName());
    	patient.setLastName(patient.getLastName());
    	patient.setPhoneNumber(request.getPhoneNumber());
    	patient.setEmail(request.getEmail());
    	patient.setState(request.getAddress().getState());
    	
    	return patient;
    }
}
