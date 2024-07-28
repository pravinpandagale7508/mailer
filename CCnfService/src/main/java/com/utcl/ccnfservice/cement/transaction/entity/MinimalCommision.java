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
@Table(name="minimalcommision")
@Data
@NoArgsConstructor
@AllArgsConstructor  
public class MinimalCommision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    minimalId;
	private Double  minQuantity;
	private Double  maxQuantity;
	private Double	slabAmountFrom;
	private Double	slabAmountTo;
	private Double	commissiononMT;
	private Double	commissionRsOnMT;
	private Double	defaultCommision;
	private Boolean isMinimumCommission;
	private Boolean isMetroCommition;
	private Double metroCommitionVal;
	private Double totalSalesCommission;
	private Double saleQuantityinMT;
	private Long    loiId;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
