package com.monitoredrx.patientservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monitoredrx.patientservice.exception.EntityNotFoundException;
import com.monitoredrx.patientservice.model.Patient;
import com.monitoredrx.patientservice.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	@Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
    	patient = new Patient();
    	patient.setId(1L);
    	patient.setFirstName("John");
    	patient.setLastName("Doe");
    	patient.setState("NY");
    }
    
    @DisplayName("Get Patients Service")
	@Nested
	@Order(1)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class GetPatientsServiceTest {
    	
    	@Test
        void shouldReturnAllPatients() {
        	
        	when(patientRepository.findAll()).thenReturn(List.of(patient));

        	List<Patient> patients = patientService.getAllPatients();
            
        	assertThat(patients).hasSize(1);
            verify(patientRepository, times(1)).findAll();
        }
    	
    }
    
    @DisplayName("Get Patient by Id Service")
	@Nested
	@Order(2)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class GetPatientByIdServiceTest {
    
    	@Test
        void shouldReturnPatientById() {
        	
            when(patientRepository.findByIdAndDeleted(any(Long.class), any(Boolean.class))).thenReturn(patient);

            Patient found = patientService.getPatientById(1L);

            assertThat(found.getFirstName()).isEqualTo("John");
        }
    	
    	@Test
        void shouldThrowExceptionWhenPatientNotFound() throws Exception {

    		when(patientRepository.findByIdAndDeleted(any(Long.class), any(Boolean.class))).thenReturn(null);
    		
    		EntityNotFoundException ex = assertThrows(
    				EntityNotFoundException.class,
    	            () -> patientService.getPatientById(1L)
    	    );
    		
    		assertEquals("Patient not found", ex.getMessage());
    	    verify(patientRepository, never()).save(any());
        }
    	
    }
    
    
    
}
