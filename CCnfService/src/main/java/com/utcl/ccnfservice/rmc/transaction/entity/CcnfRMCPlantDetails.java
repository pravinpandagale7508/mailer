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
@Table(name="ccnfrmcplantdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcnfRMCPlantDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String plantid;
	private String plantCode;
	private Date startDate;
	private Date endDate;
	private Double fixedSaleCommission;//Added to store fixed commission
}
