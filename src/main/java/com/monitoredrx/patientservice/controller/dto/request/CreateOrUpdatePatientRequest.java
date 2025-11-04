package com.monitoredrx.patientservice.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monitoredrx.patientservice.controller.dto.PatientAddressDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateOrUpdatePatientRequest {

	@Schema(description = "First name of the patient", example = "John")
	@NotBlank(message = "First name is mandatory")
	@JsonProperty("first_name")
	private String firstName;
	
	@Schema(description = "Last name of the patient", example = "Doe")
	@JsonProperty("last_name")
	private String lastName;
	
	@Schema(description = "Address details of the patient")
	private PatientAddressDto address;
	
	@Schema(description = "Phone number", example = "555-1234")
	@NotBlank(message = "Phone number is mandatory")
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	@Schema(description = "Email address", example = "john.doe@example.com")
	@NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
	private String email;

}
