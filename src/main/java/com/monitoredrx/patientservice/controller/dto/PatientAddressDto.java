package com.monitoredrx.patientservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientAddressDto {

	@Schema(description = "Patient address", example = "123 Main St")
	private String address;
	
	@Schema(description = "City", example = "Metropolis")
	private String city;
	
	@Schema(description = "State", example = "NY")
	private String state;
	
	@Schema(description = "Zip code", example = "12345")
	@JsonProperty("zip_code")
	private String zipCode;
	
}
