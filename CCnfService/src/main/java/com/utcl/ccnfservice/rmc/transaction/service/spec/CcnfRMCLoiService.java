package com.utcl.ccnfservice.rmc.transaction.service.spec;

import java.util.List;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCLoi;
import com.utcl.dto.ccnf.CcnfRMCLoiDto;

public interface CcnfRMCLoiService {

	 List<CcnfRMCLoi> getAllCcnfRMCLoi();
    
     CcnfRMCLoi getCcnfRMCLoiById(Long id) throws Exception;
  
     CcnfRMCLoiDto addCcnfRMCLoiDetails(CcnfRMCLoiDto ccnfRMCLoiDto);

   
}
