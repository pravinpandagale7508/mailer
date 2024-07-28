package com.utcl.ccnfservice.cement.transaction.entity;

import java.util.Date;
import java.util.List;

import com.utcl.ListConverter;

import jakarta.persistence.Convert;
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
@Table(name="ccnfcementloi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CCnfCementLoi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loiId;

	@Convert(converter = ListConverter.class)
	private List<Long> regionIds;

	@Convert(converter = ListConverter.class)
	private List<Long> depotIds;

	@Convert(converter = ListConverter.class)
	private List<Long> i2Dists;
	
	private Long agentId;
    private String ccnfType;
	private Date agreementStartDate;
	private Date agreementEndDate;
	private String billingCycleType;
	private Date billingCycleStartDate;
	private Date billingCycleEndDate;
	private Boolean additonalCommisionEnable;
	private Double additonalcommisionamount;
	private Boolean gubtEnable;
	private String gubtPlantCode;
	private Double gubtCommision;
	private Boolean interCompanySalesEnable;
	private Double interCompanySaleCommision;
	private Boolean interUnitSalesEnable;
	private Double interUnitSalesCommision;
	private Double interUnitSalesToOther;
	private Double interUnitSalesFromOther;
	private Boolean reduceCommisionEnable;
	private Double reduceCommisionValue;
	private Boolean fixedRentEnable;
	private Double minFixedRent;
	private Double maxFixedRent;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	// new fields

	private Double minCommitionRange;
	private Double maxCommitionRange;
	private Boolean isMetroCommition;
	private Double metroCommitionVal;
	private Boolean isMinimumCommition;
	private Double minimunSlabCommition;
	private Double gubtPlantStanderdComm;
	private Boolean isGubtHandledByCCnf;
	private String gubtPlantRate;
	private Double interCompanySaleCommition;
	private Boolean isInterCompanySale;
	private Boolean isInterunitSale;
	private Double interunitSaleCommition;
	private Boolean isOtherDepoCommission;
	private Double salesToOtherDepoComm;
	private Double salesFromOtherDepoComm;
	private Boolean isDeduOnDirSales;
	private Double slabRangeFrom; 
	private Double slabRangeTo;
	private Boolean isReducedCommByComp;
	private Double reducedCommission;
	
	private Double totalsalesQty;
	private Double totalDirectSales;
	private Double perOnDirectSales;


}
