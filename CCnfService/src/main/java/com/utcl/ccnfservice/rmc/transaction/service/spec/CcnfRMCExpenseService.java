package com.utcl.ccnfservice.rmc.transaction.service.spec;

import java.util.List;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCExpense;
import com.utcl.dto.ccnf.CcnfRMCExpenseDto;

public interface CcnfRMCExpenseService {

	 List<CcnfRMCExpense> getCcnfExpenseDetails();
    
	 CcnfRMCExpense getCcnfExpenseById(Long id) throws Exception;
  
     CcnfRMCExpenseDto addCcnfExpenseDetails(CcnfRMCExpenseDto expensedetails);

    

}
