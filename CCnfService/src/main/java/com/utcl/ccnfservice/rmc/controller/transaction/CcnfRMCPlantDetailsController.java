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

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCPlantDetails;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCPlantDetailsService;
import com.utcl.dto.ccnf.CcnfRMCPlantDetailsDto;


@RestController
@RequestMapping("/api/rmcPlant")
public class CcnfRMCPlantDetailsController {
	@Autowired
	 CcnfRMCPlantDetailsService ccnfRMCPlantDetailsService;
	
	
	
	@PostMapping("/addCcnfRMCPlantDetails")
	public ResponseEntity<CcnfRMCPlantDetailsDto> addCcnfRMCLoiDetails(@RequestBody CcnfRMCPlantDetailsDto ccnfRMCPlantDetailsDto) 
	{
		 return ResponseEntity.ok(ccnfRMCPlantDetailsService.addCcnfRMCPlantDetails(ccnfRMCPlantDetailsDto));

	}
	
	@GetMapping("/getCcnfRMCPlantDetailsById/{id}")
	public CcnfRMCPlantDetails getCcnfRMCLoiById(@PathVariable("id") long id) throws Exception
	{
		return ccnfRMCPlantDetailsService.getCcnfRMCPlantDetailsById(id);
	}

	@GetMapping("/getCcnfRMCPlantDetails")
	public List<CcnfRMCPlantDetails> getCcnfRMCLoiDetails() 
	{
		return ccnfRMCPlantDetailsService.getAllCcnfRMCPlantDetails();
	}
	
	@PutMapping("/updateCcnfRMCPlantDetails")
	public ResponseEntity<CcnfRMCPlantDetailsDto> updateCcnfRMCLoiDetails(@RequestBody CcnfRMCPlantDetailsDto ccnfRMCPlantDetailsDto) 
	{	 return ResponseEntity.ok(ccnfRMCPlantDetailsService.addCcnfRMCPlantDetails(ccnfRMCPlantDetailsDto));

	}

	
	
	
}


