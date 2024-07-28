package com.utcl.ccnfservice.cement.transaction.entity;

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
@Table(name = "slabwisecommision")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlabwiseCommision {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long slabId;
	private Double minAmount;
	private Double maxAmount;
	private Double slabAmountFrom;
	private Double slabAmountTo;
	private Double commision;
	private Double defaultcommision;
	private Double totalCommissionOnSlab;
	private Double slaesQuantityOnMT;
	private Double remSlabAmount;
	private Boolean isMetroCommition;
	private Double metroCommissionValue;
	private Long loiId;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
}
