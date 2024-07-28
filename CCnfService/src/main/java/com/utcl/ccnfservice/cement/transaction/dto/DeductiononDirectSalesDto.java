package com.utcl.ccnfservice.cement.transaction.dto;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class DeductiononDirectSalesDto {
	private Long   deductionId;
	private Double  minAmount;
	private Double  maxAmount;
	private Double  slabAmountFrom;
	private Double  slabAmountTo;
	private Double commision;
	private Long   loiId;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
