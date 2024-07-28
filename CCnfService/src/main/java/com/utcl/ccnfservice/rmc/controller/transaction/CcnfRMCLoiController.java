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

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCLoi;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCLoiService;
import com.utcl.dto.ccnf.CcnfRMCLoiDto;

@RestController
@RequestMapping("/api/rmcLoi")
public class CcnfRMCLoiController {
	@Autowired
	 CcnfRMCLoiService ccnfRMCLoiService;
	
	
	@PostMapping("/addCcnfRMCLoi")
	public ResponseEntity<CcnfRMCLoiDto> addCcnfRMCLoiDetails(@RequestBody CcnfRMCLoiDto expenseDto) 
	{
		 return ResponseEntity.ok(ccnfRMCLoiService.addCcnfRMCLoiDetails(expenseDto));

	}
	
	@GetMapping("/getCcnfRMCLoiById/{id}")
	public CcnfRMCLoi getCcnfRMCLoiById(@PathVariable("id") long id) throws Exception
	{
		return ccnfRMCLoiService.getCcnfRMCLoiById(id);
	}

	@GetMapping("/getCcnfRMCLoiDetails")
	public List<CcnfRMCLoi> getCcnfRMCLoiDetails() 
	{
		return ccnfRMCLoiService.getAllCcnfRMCLoi();
	}
	
	@PutMapping("/updateCcnfRMCLoi")
	public ResponseEntity<CcnfRMCLoiDto> updateCcnfRMCLoiDetails(@RequestBody CcnfRMCLoiDto expenseDto) 
	{
		 return ResponseEntity.ok(ccnfRMCLoiService.addCcnfRMCLoiDetails(expenseDto));

	}

	
	
	
}




