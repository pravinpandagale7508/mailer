package com.utcl.ccnfservice.rmc.transaction.service.spec;

import java.util.List;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSlabwiseCommision;

public interface CcnfRMCSlabwiseCommissionService {

	double performRMCSlabwiseCalculation(List<String> lisOfPlants, List<Double> listOfWastages, int month, int year,
			Long loiId);

	CcnfRMCSlabwiseCommision getSlabwiseRMCCommissionsById(Long slabId) throws Exception;

	List<CcnfRMCSlabwiseCommision> getSlabwiseRMCCommissions();

}
