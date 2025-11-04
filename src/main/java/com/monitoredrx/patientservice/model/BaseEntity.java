package com.monitoredrx.patientservice.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for entity", example = "1")
	private Long id;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
	@Schema(description = "Created date", example = "Sat Nov 01 03:00:55 IST 2025")
	private Date createdDate;

	@LastModifiedDate
	@Column(nullable = false)
	@Schema(description = "Modified date", example = "Sat Nov 01 03:05:55 IST 2025")
	private Date modifiedDate;

	@Column(nullable = false)
	@Schema(description = "Delete status", example = "false")
	private Boolean deleted = false;
	
}
