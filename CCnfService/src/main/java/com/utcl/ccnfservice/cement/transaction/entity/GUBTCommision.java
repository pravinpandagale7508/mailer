package com.utcl.ccnfservice.cement.transaction.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor  
public class GUBTCommision {

	@Id
	@GeneratedValue
	private Long    minimalId;
	private Double  minQuantity;
	private Double  maxQuantity;
	private Double	commissiononMT;
	private Double	defaultCommision;
	//@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "loiId")
	private Long    loiId;
//	private CCnfCementLoi cCnfCementLoi;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
