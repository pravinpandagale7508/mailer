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
public class SlabwiseCommisionDto {
	private Long    slabId;
	private Double  minAmount;
	private Double  maxAmount;
	private Double	slabAmountFrom;
	private Double	slabAmountTo;
	private Double	commision;
	private Double	defaultcommision;
	//@ManyToOne(cascade = CascadeType.ALL)
   // @JoinColumn(name = "loiId")
	//private CCnfCementLoi cCnfCementLoi;
	private Long    loiId;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
	private int quantity;
}
