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
@Table(name="deductionondirectsales")
@Data
@NoArgsConstructor
@AllArgsConstructor  
public class DeductiononDirectSales {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   deductionId;
	private Double  minAmount;
	private Double  maxAmount;
	private Double  slabAmountFrom;
	private Double  slabAmountTo;
	private Double commision;
	private Double totalDirectCommision;
	private Double quantityPercentage;
	private Double directSalesQty;
	private Long   loiId;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
