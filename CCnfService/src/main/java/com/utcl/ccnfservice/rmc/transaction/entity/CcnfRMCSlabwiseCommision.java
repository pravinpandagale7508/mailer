package com.utcl.ccnfservice.rmc.transaction.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rmcslabwisecommision")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CcnfRMCSlabwiseCommision {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long slabId;
	private Double minAmount;
	private Double maxAmount;
	private Double slabAmountFrom;
	private Double slabAmountTo;
	private Double commision;
	private Double slaesQuantityCuMT;
	private Double defaultcommision;
	private Double totalCommissionOnSlab;
	private Double remSlabAmount;
	private Long loiId;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
}
