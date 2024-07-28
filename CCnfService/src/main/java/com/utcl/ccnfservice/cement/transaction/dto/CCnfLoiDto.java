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
public class CCnfLoiDto {
	private Long  loiId;
	private Long  agentId;
	private Long  regionId;
	private Long  depotId;
	private Long  slocId;
	private Date  agreementStartDate;
	private Date  agreementEndDate;
	private String billingCycleType;
	private Date billingCycleStartDate;
	private Date billingCycleEndDate;
	private Boolean additonalCommisionEnable;
	private Double additonalcommisionamount;
	private Boolean gubtEnable;
	private String gubtPlantCode;
	private Double gubtCommision;
	private Boolean interCompanySalesEnable;
	private Double  interCompanySaleCommision;
	private Boolean interUnitSalesEnable;
	private Double interUnitSalesCommision;
	private Double interUnitSalesToOther;
	private Double interUnitSalesFromOther;
	private Boolean reduceCommisionEnable;
	private Double reduceCommisionValue;
	private Boolean fixedRentEnable;
	private Double minFixedRent;
	private Double maxFixedRent;
 	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
