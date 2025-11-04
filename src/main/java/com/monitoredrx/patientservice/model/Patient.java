package com.monitoredrx.patientservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Schema(description = "Patient entity")
public class Patient extends BaseEntity {

	@Schema(description = "First name of the patient", example = "John")
	@NotBlank(message = "First name is mandatory")
	private String firstName;
	
	@Schema(description = "Last name of the patient", example = "Doe")
	private String lastName;
	
	@Schema(description = "Patient address", example = "123 Main St")
	private String address;
	
	@Schema(description = "City", example = "Metropolis")
	private String city;
	
	@Schema(description = "State", example = "NY")
	private String state;
	
	@Schema(description = "Zip code", example = "12345")
	private String zipCode;
	
	@Schema(description = "Phone number", example = "555-1234")
	@NotBlank(message = "Phone number is mandatory")
	private String phoneNumber;
	
	@Schema(description = "Email address", example = "john.doe@example.com")
	@NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
	private String email;

}
