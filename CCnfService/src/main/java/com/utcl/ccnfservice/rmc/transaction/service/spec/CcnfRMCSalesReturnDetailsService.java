package com.utcl.ccnfservice.rmc.transaction.service.spec;

import java.util.List;

public interface CcnfRMCSalesReturnDetailsService {
	Double getConvertedReturnQtyByPlants(List<String> plants, int month, int year);

}
