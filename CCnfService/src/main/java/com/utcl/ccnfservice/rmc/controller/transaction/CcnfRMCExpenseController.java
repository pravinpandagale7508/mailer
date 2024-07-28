package com.utcl.ccnfservice.rmc.controller.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCExpense;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCExpenseService;
import com.utcl.dto.ccnf.CcnfRMCExpenseDto;

@RestController
@RequestMapping("/api/rmcExpense")
public class CcnfRMCExpenseController {

	@Autowired
	 CcnfRMCExpenseService ccnfRMCExpenseService;
	
	
	@PostMapping("/addCcnfRMCExpense")
	public ResponseEntity<CcnfRMCExpenseDto> addCcnfRMCExpenseDetails(@RequestBody CcnfRMCExpenseDto expenseDto) 
	{
		 return ResponseEntity.ok(ccnfRMCExpenseService.addCcnfExpenseDetails(expenseDto));

	}
	
	@GetMapping("/getCcnfRMCExpenseById/{id}")
	public CcnfRMCExpense getCcnfRMCExpenseById(@PathVariable("id") long id) throws Exception
	{
		return ccnfRMCExpenseService.getCcnfExpenseById(id);
	}

	@GetMapping("/getCcnfRMCExpenseDetails")
	public List<CcnfRMCExpense> getCcnfRMCExpenseDetails() 
	{
		return ccnfRMCExpenseService.getCcnfExpenseDetails();
	}
	
	@PutMapping("/updateCcnfRMCExpense")
	public ResponseEntity<CcnfRMCExpenseDto> updateCcnfRMCExpenseDetails(@RequestBody CcnfRMCExpenseDto expenseDto) 
	{
		 return ResponseEntity.ok(ccnfRMCExpenseService.addCcnfExpenseDetails(expenseDto));

	}

	
	
	
}


