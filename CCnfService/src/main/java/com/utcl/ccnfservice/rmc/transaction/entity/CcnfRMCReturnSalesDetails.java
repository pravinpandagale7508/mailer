package com.utcl.ccnfservice.rmc.transaction.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ccnfrmcreturnsalesdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcnfRMCReturnSalesDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String plant;
	private Double convertedQTY;
	private Date transactionDate;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
}
