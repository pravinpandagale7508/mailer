package com.utcl.ccnfservice.rmc.transaction.entity;

import java.util.Date;
import java.util.List;

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
@Table(name="ccnfrmcloi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcnfRMCLoi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long vendorid;
	private Long agencyid;
	private List<Long> plantids;
	private Long ccnfrmcplantdetailsid;
	private Double totalPeriod;
	private String billingCycleType;
	private Date billingCycleStartDate;
	private Date billingCycleEndDate;
	private String gstCharge;
	private Boolean ircQr;
	private Boolean isInvoiceUploadAllowed;
	private Boolean isVariableCommissionApplicable;
	private Boolean isFixedCommission;
	private Long expenseId;
	
 


}
