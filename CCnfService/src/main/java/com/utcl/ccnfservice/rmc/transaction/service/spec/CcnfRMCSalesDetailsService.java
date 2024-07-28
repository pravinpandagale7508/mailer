package com.utcl.ccnfservice.rmc.transaction.service.spec;

import java.util.List;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSalesDetails;
import com.utcl.dto.ccnf.requestResponce.CcnfRMCSalesDetailsDto;
import com.utcl.dto.rmc.requestResponce.TotalSalesQuantity;

public interface CcnfRMCSalesDetailsService {
	Double getConvertedSalesQtyByPlants(List<String> plants, int month, int year);
	CcnfRMCSalesDetails addCcnfRMCSalesDetails(CcnfRMCSalesDetailsDto ccnfRMCSalesDetailsDto);
	Double getRmcTotalSalesQuantity(List<String> lisOfPlants, List<Double> listOfWastages,
			int month, int year);
	Double getRmcTotalSalesQuantityList(List<TotalSalesQuantity> totalQty);
}
