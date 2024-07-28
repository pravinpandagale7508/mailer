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
@Table(name="ccnfrmcslabwisecommission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcnfRMCSlabwiseCommission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double minAmount;
	private Double maxAmount;
	private Double slabAmountFrom;
	private Double slabAmountTo;
	private Double commision;
	private Double defaultCommision;
	private Long loiId;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	
}
