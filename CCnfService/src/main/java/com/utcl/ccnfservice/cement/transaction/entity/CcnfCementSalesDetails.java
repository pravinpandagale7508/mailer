package com.utcl.ccnfservice.cement.transaction.entity;

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
@Table(name = "ccnfcementsalesdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcnfCementSalesDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String plant;
	private String salesOffice;
	private String salesQuantity;
	private String distributionChannel;
	private String customerGroup1;
	private String customerGroup2;
	private String salesGroup;
	private String salesOrder;
	private String region;
	private String profitCenter;
	private String invoicedate;
	private Date invDate;
	private String priceGroup;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
}
