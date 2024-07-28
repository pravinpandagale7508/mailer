package com.utcl.ccnfservice.rmc.transaction.service.spec;

import java.util.List;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCPlantDetails;
import com.utcl.dto.ccnf.CcnfRMCPlantDetailsDto;

public interface CcnfRMCPlantDetailsService {
      
	List<CcnfRMCPlantDetails> getAllCcnfRMCPlantDetails();
    
    CcnfRMCPlantDetails getCcnfRMCPlantDetailsById(Long id) throws Exception;
  
    CcnfRMCPlantDetailsDto addCcnfRMCPlantDetails(CcnfRMCPlantDetailsDto comapnydetails);

    
}
