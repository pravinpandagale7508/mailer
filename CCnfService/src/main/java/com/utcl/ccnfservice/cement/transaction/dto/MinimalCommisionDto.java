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
public class MinimalCommisionDto {
	private Long    minimalId;
	private Double  minQuantity;
	private Double  maxQuantity;
	private Double	commissiononMT;
	private Double	defaultCommision;
	private Long    loiId;
//	private CCnfCementLoi cCnfCementLoi;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
